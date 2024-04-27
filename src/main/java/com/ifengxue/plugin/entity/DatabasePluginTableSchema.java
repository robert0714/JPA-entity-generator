package com.ifengxue.plugin.entity;
 
import lombok.Getter;

public class DatabasePluginTableSchema extends TableSchema implements Selectable {

  private static final long serialVersionUID = -2363626231931472587L; 
  private boolean selected;
 

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  @Override
  public boolean isSelected() {
    return selected;
  }
}
