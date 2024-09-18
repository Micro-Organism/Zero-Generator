package ${cfg.pageDto}.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if entityLombokModel>
import lombok.Data;
</#if>
<#list table.importPackages as pkg>
 import ${pkg};
</#list>
/**
 * <p>
 * ${table.comment!}AddDtoDto
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
 @Data
 @ApiModel(value = "${table.comment!}新增对象")
 public class ${entity}AddDto{

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
   <#if  field.name != "base_id">
    @ApiModelProperty("${field.comment}")
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}
