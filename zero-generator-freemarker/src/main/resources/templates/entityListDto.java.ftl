package ${cfg.pageDto}.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if entityLombokModel>
import lombok.Data;
import com.zero.framework.core.base.dto.PageDto;
</#if>
<#list table.importPackages as pkg>
 import ${pkg};
</#list>
/**
 * <p>
 * ${table.comment!}ListDtoDto
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
 @Data
 @ApiModel(value = "${table.comment!}列表查询对象")
 public class ${entity}ListDto {
 @ApiModelProperty("分页大小")
 private Integer pageSize;
 @ApiModelProperty("页码")
 private Integer pageNum;
}
