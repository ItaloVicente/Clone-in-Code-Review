		} else if (parentEntry instanceof LogEntry) {
			entry = parentEntry;
			if (isChild(entry)) {
				setEntryChildren((AbstractEntry) entry.getParent(entry));
			} else {
				setEntryChildren();
