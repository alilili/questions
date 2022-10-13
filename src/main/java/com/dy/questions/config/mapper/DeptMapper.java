package com.dy.questions.config.mapper;

import com.dy.questions.config.entity.Organizes;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 数据库映射
 */
@Mapper
public interface DeptMapper {

    @Select("SELECT * FROM ORGANIZES WHERE ID = #{id}")
    Organizes getOrgById(String id);

    @Update("UPDATE ORGANIZES SET ORGNAME = #{orgName},SHOWORDER = #{showOrder},STATUS = #{status},LASTUPDATETIME = #{lastUpdateTime} WHERE ID = #{id}")
    boolean updateOrg(Organizes org);

    @Insert("INSERT INTO ORGANIZES (ID,ORGNAME,SHOWORDER,STATUS,CREATETIME) VALUE (#{id},#{orgName},#{showOrder},#{status},#{createTime})")
    boolean insertData(Organizes work);

    @Update("UPDATE ORGANIZES SET ROWSTATE=-1 WHERE ID = #{id}")
    boolean deleteData(String id);

    /**
     * 部门维护列表数据，保护无效
     * @return
     */
    @Select("SELECT * FROM ORGANIZES WHERE ROWSTATE=1 ORDER BY SHOWORDER")
    List<Organizes> getListData();

    /**
     * 获取所有启用部门数据
     * @return
     */
    @Select("SELECT * FROM ORGANIZES WHERE ROWSTATE=1 AND STATUS=1 ORDER BY SHOWORDER")
    List<Organizes> getOrganizes();

}