							SubmoduleWalk sWalk = SubmoduleWalk.forPath(repo
									dcTree
							ObjectId sHead = sWalk != null ? sWalk.getHead()
									: null;
							if (sHead != null)
								dcEntry.setObjectId(sHead);
							else
								dcEntry.copyMetaData(index.getEntry(dcEntry
										.getPathString()));
