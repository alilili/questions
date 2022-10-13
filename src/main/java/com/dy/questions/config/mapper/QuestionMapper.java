package com.dy.questions.config.mapper;

import com.dy.questions.config.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/2 21:48
 * @description : 调查问卷映射
 */
@Mapper
public interface QuestionMapper {

    @Select("SELECT * FROM QUESTION_CONFIG WHERE ROWSTATE > -1")
    List<Question> loadAllData();

    @Select("SELECT * FROM QUESTION_CONFIG WHERE ID = #{id}")
    Question getById(String id);

    @Insert("INSERT INTO QUESTION_CONFIG (ID,YEAR,BT,MARK,CREATEDATE,ROWSTATE,STARTDATE,ENDDATE) VALUE (#{id},#{year},#{bt},#{mark},#{createDate},#{rowstate},#{startDate},#{endDate})")
    boolean saveQuestion(Question question);

    @Update("UPDATE QUESTION_CONFIG SET ROWSTATE = -1 WHERE ID = #{id}")
    boolean delRecord(String id);

    @Update("UPDATE QUESTION_CONFIG SET YEAR = #{year},BT = #{bt},MARK = #{mark},STARTDATE = #{startDate},ENDDATE = #{endDate} WHERE ID = #{id}")
    boolean updateConfig(Question question);

    @Select("SELECT ID FROM QUESTION_CONFIG WHERE YEAR = #{year} AND ROWSTATE > -1")
    String checkConfig(String year);

    @Select("SELECT * FROM QUESTION_CONFIG WHERE ROWSTATE > -1 AND PUBLISH = 1 AND STARTDATE < NOW() AND ENDDATE > NOW()")
    Question checkPublish();

    @Update("UPDATE QUESTION_CONFIG SET PUBLISH = -1 WHERE ID = #{id}")
    boolean unPublishById(String id);

    @Update("UPDATE QUESTION_CONFIG SET PUBLISH = -1 WHERE ID != #{id}")
    boolean unPublishOther(String id);

    @Update("UPDATE QUESTION_CONFIG SET PUBLISH = 1 WHERE ID = #{id}")
    boolean publish(String id);

}