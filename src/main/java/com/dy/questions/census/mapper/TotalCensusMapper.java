package com.dy.questions.census.mapper;

import com.dy.questions.census.entity.TotalCensus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TotalCensusMapper {
    @Select("SELECT q.bt,q.id,s.question,s.questionid,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 10 OR NULL)/(COUNT(s.answer))*100,2),'%') AS excellent,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 8 OR NULL)/(COUNT(s.answer))*100,2),'%') AS good,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 6 OR NULL)/(COUNT(s.answer))*100,2),'%') AS quiteGood,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 4 OR NULL)/(COUNT(s.answer))*100,2),'%') AS commonly,\n" +
            "CONCAT(ROUND(COUNT(s.answer < 4 OR NULL)/(COUNT(s.answer))*100,2),'%') AS poor\n" +
            " FROM question_config q\n" +
            "LEFT JOIN question_reply r ON q.id = r.configid\n" +
            "LEFT JOIN question_reply_sub s ON r.id = s.fid\n" +
            "WHERE q.rowstate = 1 AND q.year = #{year} AND r.rowstate = 1\n" +
            "GROUP BY questionid")
    List<TotalCensus> getTotalCensusData(String year);

    @Select("SELECT q.bt,q.id,s.question,s.questionid,\n" +
            "COUNT(s.answer = 10 OR NULL) AS excellent,\n" +
            "COUNT(s.answer = 8 OR NULL) AS good,\n" +
            "COUNT(s.answer = 6 OR NULL) AS quiteGood,\n" +
            "COUNT(s.answer = 4 OR NULL) AS commonly,\n" +
            "COUNT(s.answer < 4 OR NULL) AS poor,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 10 OR NULL)/(COUNT(s.answer))*100,2),'%') AS excellentPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 8 OR NULL)/(COUNT(s.answer))*100,2),'%') AS goodPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 6 OR NULL)/(COUNT(s.answer))*100,2),'%') AS quiteGoodPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 4 OR NULL)/(COUNT(s.answer))*100,2),'%') AS commonlyPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer < 4 OR NULL)/(COUNT(s.answer))*100,2),'%') AS poorPer\n" +
            " FROM question_config q\n" +
            "LEFT JOIN question_reply r ON q.id = r.configid\n" +
            "LEFT JOIN question_reply_sub s ON r.id = s.fid\n" +
            "WHERE q.rowstate = 1 AND q.year = #{year} AND r.rowstate = 1\n" +
            "GROUP BY questionid")
    List<TotalCensus> getTotalCensusExcelData(String year);

    @Select("SELECT q.bt,s.question,\n" +
            "COUNT(s.answer = 10 OR NULL) AS excellent,\n" +
            "COUNT(s.answer = 8 OR NULL) AS good,\n" +
            "COUNT(s.answer = 6 OR NULL) AS quiteGood,\n" +
            "COUNT(s.answer = 4 OR NULL) AS commonly,\n" +
            "COUNT(s.answer < 4 OR NULL) AS poor,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 10 OR NULL)/(COUNT(s.answer))*100,2),'%') AS excellentPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 8 OR NULL)/(COUNT(s.answer))*100,2),'%') AS goodPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 6 OR NULL)/(COUNT(s.answer))*100,2),'%') AS quiteGoodPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer = 4 OR NULL)/(COUNT(s.answer))*100,2),'%') AS commonlyPer,\n" +
            "CONCAT(ROUND(COUNT(s.answer < 4 OR NULL)/(COUNT(s.answer))*100,2),'%') AS poorPer\n" +
            " FROM question_config q\n" +
            "LEFT JOIN question_reply r ON q.id = r.configid\n" +
            "LEFT JOIN question_reply_sub s ON r.id = s.fid\n" +
            "WHERE q.rowstate = 1 AND q.year = 2022 AND r.rowstate = 1 AND s.questionid = #{questionid}\n" +
            "GROUP BY questionid\n")
    TotalCensus getTotalBarData(String questionId);

    @Select("SELECT COUNT(s.answer) as quesCount,ROUND(SUM(s.answer)/COUNT(s.answer),2) as cStore\n" +
            " FROM question_config q\n" +
            "LEFT JOIN question_reply r ON q.id = r.configid\n" +
            "LEFT JOIN question_reply_sub s ON r.id = s.fid\n" +
            "WHERE q.rowstate = 1 AND q.year = #{year} AND r.rowstate = 1")
    TotalCensus getTotalCensusTopData(String year);
}
