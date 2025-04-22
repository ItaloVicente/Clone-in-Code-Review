        currentOp.readFromBuffer(rbuf);
        if (currentOp.getState() == OperationState.COMPLETE) {
          getLogger().debug("Completed read op: %s and giving the next %d "
              + "bytes", currentOp, rbuf.remaining());
          Operation op = qa.removeCurrentReadOp();
          assert op == currentOp : "Expected to pop " + currentOp + " got "
              + op;
          currentOp = qa.getCurrentReadOp();
        } else if (currentOp.getState() == OperationState.RETRY) {
          getLogger().debug("Reschedule read op due to NOT_MY_VBUCKET error: "
              + "%s ", currentOp);
          ((VBucketAware) currentOp).addNotMyVbucketNode(currentOp
              .getHandlingNode());
          Operation op = qa.removeCurrentReadOp();
          assert op == currentOp : "Expected to pop " + currentOp + " got "
              + op;
          retryOps.add(currentOp);
          currentOp = qa.getCurrentReadOp();
        }
      }
      rbuf.clear();
      read = channel.read(rbuf);
    }
  }

  static String dbgBuffer(ByteBuffer b, int size) {
    StringBuilder sb = new StringBuilder();
    byte[] bytes = b.array();
    for (int i = 0; i < size; i++) {
      char ch = (char) bytes[i];
      if (Character.isWhitespace(ch) || Character.isLetterOrDigit(ch)) {
        sb.append(ch);
      } else {
        sb.append("\\x");
        sb.append(Integer.toHexString(bytes[i] & 0xff));
      }
