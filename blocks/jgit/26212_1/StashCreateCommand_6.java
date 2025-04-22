				ObjectId untrackedCommit = null;
				if (!untracked.isEmpty()) {
					DirCache untrackedDirCache = DirCache.newInCore();
					DirCacheBuilder untrackedBuilder = untrackedDirCache
							.builder();
					for (DirCacheEntry entry : untracked)
						untrackedBuilder.add(entry);
					untrackedBuilder.finish();

					builder.setParentIds(new ObjectId[0]);
					builder.setTreeId(untrackedDirCache.writeTree(inserter));
					builder.setMessage(MessageFormat.format(MSG_UNTRACKED
							branch
							headCommit.getShortMessage()));
					untrackedCommit = inserter.insert(builder);
				}

