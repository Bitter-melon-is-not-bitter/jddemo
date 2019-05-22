package com.tang.jddemo.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * HttpClient抓包工具类
 */
@Component
public class HttpClientUtils {

    private PoolingHttpClientConnectionManager pc;

    public HttpClientUtils() {
        if (null == pc) {
            this.pc = new PoolingHttpClientConnectionManager();
        }

        //设置最大连接数
        pc.setMaxTotal(100);

        //设置主机连接数
        pc.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据url下载页面数据（get方式）
     *
     * @return
     */
    public String doGetHtml(String url) {
        //用连接池管理器创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.pc).build();

        //创建get请求方式
        HttpGet get = new HttpGet(url);

        //设置get请求信息
        get.setConfig(this.getConfig());

        //切记 在爬取京东手机信息时一定要设置这条头部信息  这条头部信息可以在自己的浏览器的开发者工具里面找到 其他人不知道 反正我是需要设置 不然就会爬取不到指定网页的信息
        get.setHeader("user-agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3724.8 Safari/537.36\n");

        //返回响应
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(get);
            //对响应码为200的进行处理
            if (response.getStatusLine().getStatusCode() == 200) {
                if (null != response.getEntity()) {
                    //得到数据
                    String content = EntityUtils.toString(response.getEntity(), "utf-8");
                    //返回数据
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    //关闭response释放资源，因为httpclient由连接池管理，所以不用自己关闭httpclient
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //没有数据处理
        return "请求失败";
    }

    /**
     * 设置请求信息
     *
     * @return
     */
    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5000)//设置连接最长时间
                .setConnectionRequestTimeout(500)//获取连接最长时间
                .setSocketTimeout(10000)//设置数据传输最长时间
                .build();
        return config;
    }

    /**
     * 下载图片（get方式）
     *
     * @param url
     * @return
     */
    public String downloadImg(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.pc).build();

        HttpGet get = new HttpGet(url);

        get.setConfig(this.getConfig());
        get.setHeader("user-agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3724.8 Safari/537.36\n");

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (null != response.getEntity()) {
                    //根据url获取后缀名
                    String extName = url.substring(url.lastIndexOf("."));

                    //用UUID给图片生成新名字
                    String imgName = UUID.randomUUID().toString() + extName;

                    //获取项目所在位置
                    String property = System.getProperty("user.dir");

                    //获取output流对象
                    OutputStream os = new FileOutputStream(new File(property + "\\src\\main\\resources\\static\\" + imgName));

                    //下载图片
                    response.getEntity().writeTo(os);

                    //关闭os
                    os.close();
                    System.out.println(imgName);
                    return imgName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "下载失败";
    }
}
