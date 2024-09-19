package com.zero.generator.domain;

import com.zero.generator.util.ZeroStringUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 分页数据
 *
 * @author zero
 */
@Data
public class PageDomain {
    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    private String isAsc = "asc";

    /**
     * 分页参数合理化
     */
    private Boolean reasonable = true;

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return ZeroStringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

}
