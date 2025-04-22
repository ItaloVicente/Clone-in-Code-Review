  public final boolean hasNext() {
    if (currentState == State.FINISHED) {
      return false;
    }

    if (alreadyCalled) {
