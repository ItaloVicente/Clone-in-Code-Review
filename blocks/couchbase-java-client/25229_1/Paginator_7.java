  final State getCurrentState() {
    return currentState;
  }

  enum State {
    INITIALIZED,
    PAGING,
    FINISHED
