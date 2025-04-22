    } else {
      alreadyCalled = true;
    }

    fetchNextPage();
    if (currentState == State.INITIALIZED) {
      currentState = State.PAGING;
