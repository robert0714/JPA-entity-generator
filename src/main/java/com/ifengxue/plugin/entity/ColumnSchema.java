package com.ifengxue.plugin.entity;

import java.io.Serializable;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@jakarta.persistence.Table(name = "information_schema.COLUMNS")
public class ColumnSchema implements Serializable {

  private static final long serialVersionUID = -7523969607822355567L;
  @Id
  @jakarta.persistence.Column(name = "COLUMN_NAME")
  private String columnName;

  @jakarta.persistence.Column(name = "TABLE_SCHEMA")
  private String tableSchema;

  @jakarta.persistence.Column(name = "TABLE_NAME")
  private String tableName;

  @jakarta.persistence.Column(name = "ORDINAL_POSITION")
  private int ordinalPosition;

  @jakarta.persistence.Column(name = "DATA_TYPE")
  private String dataType;

  @jakarta.persistence.Column(name = "COLUMN_TYPE")
  private String columnType;

  @jakarta.persistence.Column(name = "EXTRA")
  private String extra;

  @jakarta.persistence.Column(name = "COLUMN_COMMENT")
  private String columnComment;

  @jakarta.persistence.Column(name = "IS_NULLABLE")
  private String isNullable;

  @jakarta.persistence.Column(name = "COLUMN_DEFAULT")
  private String columnDefault;

  @jakarta.persistence.Column(name = "COLUMN_KEY")
  private String columnKey;

}
