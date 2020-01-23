package com.rbgt.obtain.mapper;

import com.rbgt.obtain.entity.NbaTmoneyInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface NbaTmoneyInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(NbaTmoneyInfo record);

    int insertSelective(NbaTmoneyInfo record);

    NbaTmoneyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NbaTmoneyInfo record);

    int updateByPrimaryKey(NbaTmoneyInfo record);
    
    List<NbaTmoneyInfo> selectByCreateTime(NbaTmoneyInfo record);
}