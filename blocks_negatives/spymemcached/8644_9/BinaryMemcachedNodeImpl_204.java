			while(writeQ.peek() instanceof GetOperation
					&& og.size() < MAX_GET_OPTIMIZATION_COUNT) {
				GetOperation o=(GetOperation) writeQ.remove();
				if(!o.isCancelled()) {
					og.addOperation(o);
				}
			}
