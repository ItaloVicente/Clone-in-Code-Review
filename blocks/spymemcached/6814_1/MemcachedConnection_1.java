				} else if (currentOp.getState() == OperationState.RETRY) {
                    getLogger().debug(
                            "Reschedule read op due to NOT_MY_VBUCKET error: %s ",
                            currentOp);
                    Operation op=qa.removeCurrentReadOp();
                    assert op == currentOp
                    : "Expected to pop " + currentOp + " got " + op;
                    retryOps.add(currentOp);
                    currentOp=qa.getCurrentReadOp();

