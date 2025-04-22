			if (childrenToRestore.size() > 1) {
				Set<IUndoableOperation> undoHistory = new HashSet<>();
				for (IUndoContext context : this.getContexts()) {
					if (context != null) {
						undoHistory.addAll(Arrays.asList(history.getUndoHistory(context)));
					}
				}
				if (undoHistory.contains(this)) {
					Collections.reverse(childrenToRestore);
				}
			}
