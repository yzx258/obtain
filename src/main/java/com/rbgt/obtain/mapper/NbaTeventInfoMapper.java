package com.rbgt.obtain.mapper;

import com.rbgt.obtain.entity.NbaTeventInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface NbaTeventInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(NbaTeventInfo record);

    int insertSelective(NbaTeventInfo record);

    NbaTeventInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NbaTeventInfo record);

    int updateByPrimaryKey(NbaTeventInfo record);
    
    NbaTeventInfo selectByNameAndTime(NbaTeventInfo record);
    
    NbaTeventInfo selectByName(NbaTeventInfo record);
    
    List<NbaTeventInfo> selectByTime(NbaTeventInfo record);
    
    List<NbaTeventInfo> selectByExtension1(NbaTeventInfo record);
    
    
    
}