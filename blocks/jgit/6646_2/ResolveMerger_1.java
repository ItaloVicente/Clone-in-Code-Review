			DirCacheEntry entry = add(tw.getRawPath()
					DirCacheEntry.STAGE_0);
			if (workingTreeIterator != null) {
				entry.setLength(workingTreeIterator.getEntryLength());
				entry.setLastModified(workingTreeIterator
						.getEntryLastModified());
			}
