				}

				String encoding = null;

				GitFileRevision compareRev = null;
				if (compareVersionIterator != null) {
					String entryPath = compareVersionIterator.getEntryPathString();
					encoding = CompareCoreUtils.getResourceEncoding(repository, entryPath);
