package com.dy.questions.demo.mapper;

import com.dy.questions.demo.entity.Test;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/1 00:07
 * @description : 数据库映射
 */
@Mapper
public interface TestMapper {

    @Select("SELECT * FROM TEST WHERE ID = #{id}")
    Test getTestById(String id);

    @Update("UPDATE TEST SET NAME = #{name} WHERE ID = #{id}")
    boolean updateName(String name,String id);

    @Insert("INSERT INTO TEST (ID,name) VALUE (#{id},#{name})")
    int insertData(Test work);

    @Delete("DELETE FROM TEST WHERE ID = #{id}")
    int deleteData(String id);

    @Select("SELECT * FROM TEST WHERE name like CONCAT('%',#{name},'%')")
    List<Test> getListData(String name);

}