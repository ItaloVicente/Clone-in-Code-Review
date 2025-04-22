				if (state == RepositoryState.MERGING_RESOLVED) {
					File mergeHeadsFile = new File(repo.getDirectory()
							Constants.MERGE_HEAD);
					if (mergeHeadsFile.exists())
						mergeHeadsFile.delete();
					File mergeMsgFile = new File(repo.getDirectory()
							Constants.MERGE_MSG);
					if (mergeMsgFile.exists())
						mergeMsgFile.delete();
				}

