				currentOp.readFromBuffer(rbuf);
				if(currentOp.getState() == OperationState.COMPLETE) {
					getLogger().debug(
							"Completed read op: %s and giving the next %d bytes",
							currentOp, rbuf.remaining());
					Operation op=qa.removeCurrentReadOp();
					assert op == currentOp
					: "Expected to pop " + currentOp + " got " + op;
					currentOp=qa.getCurrentReadOp();
				} else if (currentOp.getState() == OperationState.RETRY) {
                    getLogger().warn(
                            "Reschedule read op due to NOT_MY_VBUCKET error: %s ",
                            currentOp);
                    ((VBucketAware) currentOp).addNotMyVbucketNode(currentOp.getHandlingNode());
                    Operation op=qa.removeCurrentReadOp();
                    assert op == currentOp
                    : "Expected to pop " + currentOp + " got " + op;
                    retryOps.add(currentOp);
                    currentOp=qa.getCurrentReadOp();

