			} else {
				if (dirCacheIterator != null) {
					added.add(dirCacheIterator.getEntryPathString());
					changesExist = true;
				} else {
					if (workingTreeIterator != null
							&& !workingTreeIterator.isEntryIgnored()) {
						untracked.add(workingTreeIterator.getEntryPathString());
						changesExist = true;
