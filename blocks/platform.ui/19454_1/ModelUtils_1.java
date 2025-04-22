	
  public static enum Position {
    LAST("last"),

    FIRST("first"),

    BEFORE("before:"),

    AFTER("after:"),

    INDEX("index:");

    private final String prefix;

    private Position(String prefix) {
      assert prefix != null : "Prefix required!";
      this.prefix = prefix;
    }

    public static final Position find(String positionInfo) {
      if (positionInfo == null || positionInfo.isEmpty())
        return null;

      for (Position position : Position.values()) {
        if (positionInfo.startsWith(position.prefix))
          return position;
      }

      return null;
    }
  }

  public static final class PositionInfo {
    private final Position position;

    private final String positionReference;

    public static final PositionInfo FIRST = new PositionInfo(Position.FIRST, null);

    public static final PositionInfo LAST = new PositionInfo(Position.LAST, null);

    public PositionInfo(Position position, String positionReference) {
      if (position == null) {
        throw new NullPointerException("No position given!");
      }

      this.position = position;
      this.positionReference = positionReference;
    }

    public Position getPosition() {
      return position;
    }

    public String getPositionReference() {
      return positionReference;
    }

    public int getPositionReferenceAsInteger() {
      return Integer.parseInt(positionReference);
    }

    public static PositionInfo parse(String positionInfo) {
      Position position = Position.find(positionInfo);
      if (position != null) {
        switch (position) {
          case FIRST:
            return PositionInfo.FIRST;

          case LAST:
            return PositionInfo.LAST;

          default:
            return new PositionInfo(position, positionInfo.substring(position.prefix.length()));
        }
      }

      return null;
    }
