package com.dy.questions.config.mapper;

import com.dy.questions.config.entity.Question;
import com.dy.questions.config.entity.QuestionSub;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/2 22:21
 * @description : 调查问卷问题子表映射
 */
@Mapper
public interface QuestionSubMapper {

    @Insert("INSERT INTO QUESTION_CONFIG_SUB (ID,ORGID,FID,QUESTION,ROWSTATE,SHOWORDER) VALUE (#{id},#{orgId},#{fid},#{question},#{rowstate},#{showOrder})")
    boolean saveQuestionSub(QuestionSub sub);

    @Select("SELECT * FROM QUESTION_CONFIG_SUB WHERE FID = #{fid} AND ROWSTATE > -1 ORDER BY SHOWORDER ")
    @Results(id="queryQuestionSub",value={
        @Result(property = "organize",column = "orgId",one=
         @One(select="com.dy.questions.config.mapper.DeptMapper.getOrgById",fetchType= FetchType.DEFAULT))
    })
    List<QuestionSub> queryQuestionSub(String fid);

    @Update("UPDATE QUESTION_CONFIG_SUB SET ROWSTATE = -1 WHERE ID = #{subId}")
    boolean deleteSub(String subId);

    @Update("UPDATE QUESTION_CONFIG_SUB SET ORGID = #{orgId},QUESTION=#{question},SHOWORDER=#{showOrder} WHERE ID = #{id}")
    boolean updateSub(QuestionSub sub);

}