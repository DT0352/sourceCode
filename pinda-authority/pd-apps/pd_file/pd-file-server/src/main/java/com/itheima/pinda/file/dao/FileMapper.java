package com.itheima.pinda.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.pinda.file.domain.FileStatisticsDO;
import com.itheima.pinda.file.entity.File;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mapper 接口
 * 文件表
 */
@Repository
public interface FileMapper extends BaseMapper<File> {
    /**
     * 按照日期类型，时间区间，来查询指定用户的各种类型的 数量和大小
     *
     * @param userId
     * @param dateType  日期类型
     * @param dataType  数据类型 数据类型=ALL 按类型统计所有， =指定类型时（不等null）， 统计指定类型 ， =null 时不区分类型统计所有
     * @param startTime
     * @param endTime
     * @return FileStatisticsDO
     */
    List<FileStatisticsDO> findNumAndSizeByUserId(@Param("userId") Long userId,
                                                  @Param("dateType") String dateType,
                                                  @Param("dataType") String dataType,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);
}
