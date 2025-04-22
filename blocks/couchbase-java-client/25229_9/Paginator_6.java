      currentState = State.FINISHED;
    }

    currentPage++;
  }

  public final ViewResponse next() {
    alreadyCalled = false;

    if (currentState == State.INITIALIZED) {
