			if (currentOp instanceof TapOperation) {
				currentOp.getCallback().complete();
				((TapOperation)currentOp).streamClosed(OperationState.COMPLETE);
				getLogger().debug(
						"Completed read op: %s and giving the next %d bytes",
						currentOp, rbuf.remaining());
				Operation op=qa.removeCurrentReadOp();
				assert op == currentOp : "Expected to pop " + currentOp + " got " + op;
				queueReconnect(qa);
				currentOp=qa.getCurrentReadOp();
			} else {
			    throw new IOException("Disconnected unexpected, will reconnect.");
			}
