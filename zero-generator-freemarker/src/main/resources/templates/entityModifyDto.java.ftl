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
 * ${table.comment!}ModifyDto
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
 @Data
 @ApiModel(value = "${table.comment!}修改对象")
 public class ${entity}ModifyDto {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    @ApiModelProperty("${field.comment}")
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}
