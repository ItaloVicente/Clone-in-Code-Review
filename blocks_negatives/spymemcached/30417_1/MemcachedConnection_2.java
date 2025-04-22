          currentOp.readFromBuffer(rbuf);

          if (currentOp.getState() == OperationState.COMPLETE) {
            getLogger().debug("Completed read op: %s and giving the next %d "
              + "bytes", currentOp, rbuf.remaining());
            Operation op = node.removeCurrentReadOp();
            assert op == currentOp : "Expected to pop " + currentOp + " got "
              + op;

            if (op.hasErrored()) {
              metrics.markMeter(OVERALL_RESPONSE_FAIL_METRIC);
            } else {
              metrics.markMeter(OVERALL_RESPONSE_SUCC_METRIC);
            }
          } else if (currentOp.getState() == OperationState.RETRY) {
            getLogger().debug("Reschedule read op due to NOT_MY_VBUCKET error: "
              + "%s ", currentOp);
            ((VBucketAware) currentOp).addNotMyVbucketNode(
              currentOp.getHandlingNode());
            Operation op = node.removeCurrentReadOp();
            assert op == currentOp : "Expected to pop " + currentOp + " got "
              + op;

            retryOps.add(currentOp);
            metrics.markMeter(OVERALL_RESPONSE_RETRY_METRIC);
          }
