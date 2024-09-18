package ${cfg.pageDto}.vo;
<#list table.importPackages as pkg>
import ${pkg};
</#list>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if entityLombokModel>
import lombok.Data;
import com.zero.framework.core.base.vo.BaseVo;
</#if>

/**
 * <p>
 * ${table.comment!}Vo
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel(value = "${entity}Vo对象", description = "${table.comment!}")
public class ${entity}Vo extends BaseVo {

 <#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    @ApiModelProperty(value = "${field.comment}")
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
