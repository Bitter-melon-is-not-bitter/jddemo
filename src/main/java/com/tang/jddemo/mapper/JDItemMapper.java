package com.tang.jddemo.mapper;

import com.tang.jddemo.model.JDItem;
import com.tang.jddemo.model.JDItemExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JDItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int countByExample(JDItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int deleteByExample(JDItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int insert(JDItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int insertSelective(JDItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    List<JDItem> selectByExample(JDItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    JDItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int updateByExampleSelective(@Param("record") JDItem record, @Param("example") JDItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int updateByExample(@Param("record") JDItem record, @Param("example") JDItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int updateByPrimaryKeySelective(JDItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jd_item
     *
     * @mbggenerated Tue May 21 17:48:49 CST 2019
     */
    int updateByPrimaryKey(JDItem record);

    List<JDItem> selectBysku(Long sku);

}