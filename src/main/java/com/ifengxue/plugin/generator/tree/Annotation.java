package com.ifengxue.plugin.generator.tree;

import com.ifengxue.plugin.util.StringHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Annotation extends AbstractElement {

  private final String className;
  private final String annotationName;
  private boolean insertAtSymbol;
  private Set<KeyValuePair> keyValuePairs = new HashSet<>();

  public Annotation(String className) {
    this(className, true);
  }

  public Annotation(String className, boolean insertAtSymbol) {
    this.className = className;
    this.annotationName = StringHelper.parseSimpleName(className);
    this.insertAtSymbol = insertAtSymbol;
  }

  public Annotation addKeyValuePair(KeyValuePair keyValuePair) {
    keyValuePairs.add(keyValuePair);
    return this;
  }

  public boolean isInsertAtSymbol() {
    return insertAtSymbol;
  }

  public void setInsertAtSymbol(boolean insertAtSymbol) {
    this.insertAtSymbol = insertAtSymbol;
  }

  public String getClassName() {
    return className;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(isInsertAtSymbol() ? "@" : "").append(annotationName);
    if (keyValuePairs.isEmpty()) {
      return builder.toString();
    }
    if (keyValuePairs.size() == 1 && new ArrayList<>(keyValuePairs).get(0).getKey().equals("value")) {
      return builder.append("(")
          .append(new ArrayList<>(keyValuePairs).get(0).getValue())
          .append(")")
          .toString();
    }
    builder.append("(");
    builder.append(keyValuePairs.stream()
        .map(keyValuePair -> keyValuePair.getKey() + " = " + keyValuePair.getValue())
        .collect(Collectors.joining(", ")));
    builder.append(")");
    return builder.toString();
  }
}
