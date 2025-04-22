			synchronized(o) {
				if (o.isCancelled()) {
					getLogger().debug("Not writing cancelled op.");
					Operation cancelledOp = removeCurrentWriteOp();
					assert o == cancelledOp;
				} else if (o.isTimedOut(defaultOpTimeout)) {
					getLogger().debug("Not writing timed out op.");
					Operation timedOutOp = removeCurrentWriteOp();
					assert o == timedOutOp;
				} else {
					o.writing();
					if (!(o instanceof TapAckOperationImpl)) {
						readQ.add(o);
					}
					return o;
