			if (ref instanceof LooseSymbolicRef
					&& ref.getSnapshot().equals(snapshot)
					&& ref.getTarget().getName().equals(target)) {
				ref.getSnapshot().setClean(snapshot);
				return ref;
			}
			return newSymbolicRef(snapshot
