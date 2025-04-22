			if (o != null && (o.isCancelled())) {
				getLogger().debug("Not writing cancelled op.");
				Operation cancelledOp = removeCurrentWriteOp();
				assert o == cancelledOp;
				return;
                        }
			if (o != null && o.isTimedOut(defaultOpTimeout)) {
				getLogger().debug("Not writing timed out op.");
				Operation timedOutOp = removeCurrentWriteOp();
				assert o == timedOutOp;
				return;
