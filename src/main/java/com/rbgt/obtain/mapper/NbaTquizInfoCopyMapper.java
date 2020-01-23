package com.rbgt.obtain.mapper;

import com.rbgt.obtain.entity.NbaTquizInfoCopy;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface NbaTquizInfoCopyMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(NbaTquizInfoCopy record);

    int insertSelective(NbaTquizInfoCopy record);

    NbaTquizInfoCopy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NbaTquizInfoCopy record);

    int updateByPrimaryKey(NbaTquizInfoCopy record);
    
    List<NbaTquizInfoCopy> selectByCreateTime(@Param("quizCreateTime") String quizCreateTime);
    
    NbaTquizInfoCopy selectByCreateTimeAndUpdate(NbaTquizInfoCopy record);
    
}