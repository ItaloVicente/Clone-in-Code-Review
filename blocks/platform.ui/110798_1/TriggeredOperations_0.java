			if (childrenToRestore.size() > 1) {
				Set undoHistory = new HashSet();
				IUndoContext[] undoContexts = this.getContexts();
				for (int i = 0; i < undoContexts.length; i++) {
					IUndoContext context = undoContexts[i];
					if (context != null) {
						undoHistory.addAll(Arrays.asList(history.getUndoHistory(context)));
					}
				}
				if (undoHistory.contains(this)) {
					Collections.reverse(childrenToRestore);
				}
			}
