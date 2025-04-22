
    fetchNextPage();
    if (currentState == State.INITIALIZED) {
      currentState = State.PAGING;
    }

    return true;
