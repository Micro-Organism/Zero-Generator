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
 * ${table.comment!}Dto
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
 @Data
 @ApiModel(value = "${entity}Dto对象", description = "${table.comment!}")
 public class ${entity}Dto extends PageDto {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    @ApiModelProperty("${field.comment}")
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}
