package org.eclipse.e4.ui.model.internal;

public enum Position {
  LAST("last"),

  FIRST("first"),

  BEFORE("before:"),

  AFTER("after:"),

  INDEX("index:");

  final String prefix;

  private Position(String prefix) {
    assert prefix != null : "Prefix required!";
    this.prefix = prefix;
  }

  public static final Position find(String positionInfo) {
    if (positionInfo == null || positionInfo.length() <= 0)
      return null;

    for (Position position : Position.values()) {
      if (positionInfo.startsWith(position.prefix))
        return position;
    }

    return null;
  }
}
