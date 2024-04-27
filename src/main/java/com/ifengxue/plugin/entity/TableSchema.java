package com.ifengxue.plugin.entity;

import java.io.Serializable;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@jakarta.persistence.Table(name = "information_schema.TABLES")
public class TableSchema implements Serializable {

  private static final long serialVersionUID = 1853575310189734827L;
  @Id
  @jakarta.persistence.Column(name = "TABLE_NAME")
  private String tableName;

  @jakarta.persistence.Column(name = "TABLE_COMMENT")
  private String tableComment;

  @jakarta.persistence.Column(name = "TABLE_CATALOG")
  private String tableCatalog;

  @jakarta.persistence.Column(name = "TABLE_SCHEMA")
  private String tableSchema;
}
