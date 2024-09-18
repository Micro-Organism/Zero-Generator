package ${cfg.pageDto}.vo;
<#list table.importPackages as pkg>
import ${pkg};
</#list>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if entityLombokModel>
import lombok.Data;
</#if>

/**
 * <p>
 * ${table.comment!}Detail
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel(value = "${table.comment!}详情展示对象")
public class ${entity}Detail {

 <#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    @ApiModelProperty(value = "${field.comment}")
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
