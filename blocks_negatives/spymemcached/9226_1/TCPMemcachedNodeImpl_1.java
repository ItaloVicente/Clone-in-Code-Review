
          o = getCurrentWriteOp();
          if (o != null) {
            o.writing();
            if (!(o instanceof TapAckOperationImpl)) {
              readQ.add(o);
            }
          }
