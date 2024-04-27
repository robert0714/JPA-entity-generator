package com.ifengxue.plugin.entity;
 
import com.ifengxue.plugin.util.StringHelper;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.mybatis.generator.internal.db.SqlReservedWords;

@Data
@Accessors(chain = true) 
public class Column implements Selectable {

  /**
   * 是否被选择，选择后才可生成类
   */ 
  private boolean selected;
  /**
   * 序号
   */ 
  private int sequence;
  /**
   * 数据库字段名
   */ 
  private String columnName;
  /**
   * 实体字段名
   */ 
  private String fieldName;
  /**
   * 字段顺序
   */
  private int sort;
  /**
   * 数据库数据类型
   */
  private String dbDataType;
  /**
   * @see ColumnSchemaExtension#jdbcType()
   */
  private int jdbcType;
  /**
   * @see ColumnSchemaExtension#jdbcTypeName()
   */
  private String jdbcTypeName;
  /**
   * Java数据类型
   */ 
  private Class<?> javaDataType;
  /**
   * 是否是主键
   */
  private boolean primary;
  /**
   * 是否允许为null
   */ 
  private boolean nullable = true;
  /**
   * 是否允许为null或空串或仅包含空白字符的字符串
   */ 
  private boolean notBlank;
  /**
   * 是否允许为null或空串
   */ 
  private boolean notEmpty;
  /**
   * 是否是自增字段
   */
  private boolean autoIncrement;
  private boolean sequenceColumn;
  /**
   * 是否有默认值
   */
  private boolean hasDefaultValue;
  /**
   * 默认值，如果是字符串则默认值是"默认值"
   */
  private String defaultValue;
  /**
   * 字段注释
   */ 
  private String columnComment;
  /**
   * 字段注解
   */
  private List<String> annotations;

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  @Override
  public boolean isSelected() {
    return selected;
  }

  public boolean isReservedWord() {
    return SqlReservedWords.containsWord(columnName);
  }

  public String getGetterMethodName() {
    if (javaDataType == boolean.class) {
      return StringHelper.parseIsMethodName(fieldName);
    }
    return StringHelper.parseGetMethodName(fieldName);
  }

  public String getSetterMethodName() {
    return StringHelper.parseSetMethodName(fieldName);
  }
}
