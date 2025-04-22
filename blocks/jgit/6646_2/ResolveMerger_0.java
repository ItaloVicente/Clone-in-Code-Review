
			if (workingTreeIterator != null) {
				entry.setLength(workingTreeIterator.getEntryLength());
				entry.setLastModified(workingTreeIterator
						.getEntryLastModified());
			}
			return true;
