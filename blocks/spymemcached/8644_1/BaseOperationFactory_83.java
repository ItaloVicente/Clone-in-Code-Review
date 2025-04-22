  public Collection<Operation> clone(KeyedOperation op) {
    assert (op.getState() == OperationState.WRITING || op.getState()
        == OperationState.RETRY) : "Who passed me an operation in the "
        + op.getState() + "state?";
    assert !op.isCancelled() : "Attempted to clone a canceled op";
    assert !op.hasErrored() : "Attempted to clone an errored op";
