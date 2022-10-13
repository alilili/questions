package com.dy.questions.publish.mapper;

import com.dy.questions.publish.entity.Reply;
import com.dy.questions.publish.entity.ReplySub;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 数据库映射
 */
@Mapper
public interface PublishMapper {

    @Insert("INSERT INTO QUESTION_REPLY (ID,YEAR,BT,IP,CREATETIME,ROWSTATE,configId,userName,age,mobile,address,email,work,cardId)" +
            " VALUE (#{id},#{year},#{bt},#{ip},#{createTime},1,#{configId},#{userName},#{age},#{mobile},#{address},#{email},#{work},#{cardId})")
    boolean saveReply(Reply question);

    @Insert("INSERT INTO QUESTION_REPLY_SUB(ID,FID,QUESTIONID,ANSWER,QUESTION,CONFIGID,SHOWORDER,ROWSTATE,ANSWERTEXT) " +
            " VALUE (#{id},#{fid},#{questionId},#{answer},#{question},#{configId},#{showOrder},1,#{answerText})")
    boolean saveReplySub(ReplySub sub);

    @Select("SELECT IP,COUNT(*) AS TOTAL FROM QUESTION_REPLY WHERE date_format(CREATETIME,'%Y-%m-%d')=#{replyDay} GROUP BY ip")
    List getReplyDayCount(String replyDay);

    //年底清空提交用户信息
    @Update("UPDATE QUESTION_REPLY SET USERNAME='***',AGE=NULL,MOBILE=NULL,ADDRESS=NULL,EMAIL=NULL,WORK=NULL,CARDID=NULL WHERE YEAR=#{YEAR} ")
    boolean clearUserInfo(int year);

}