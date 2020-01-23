package com.rbgt.obtain.mapper;

import com.rbgt.obtain.entity.NbaTquizInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface NbaTquizInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(NbaTquizInfo record);

    int insertSelective(NbaTquizInfo record);

    NbaTquizInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NbaTquizInfo record);

    int updateByPrimaryKey(NbaTquizInfo record);
    
    NbaTquizInfo selectByNameAndTime(NbaTquizInfo record);
    
    NbaTquizInfo selectByName(NbaTquizInfo record);
    
    List<NbaTquizInfo> selectByTime(NbaTquizInfo record);
    
    List<NbaTquizInfo> selectByNameLink(NbaTquizInfo record);
    
}