			if (e.mergeInto(current)) {
				disposeEntry(e);
				removeForwardEntries();
			} else {
				add(e);
			}
