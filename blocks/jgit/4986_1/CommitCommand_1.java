							SubmoduleWalk sWalk = SubmoduleWalk.forPath(repo
									dcTree
							ObjectId submoduleHead = sWalk != null ? sWalk
									.getHead() : null;
							if (submoduleHead != null)
								dcEntry.setObjectId(submoduleHead);
							else
								dcEntry.copyMetaData(index.getEntry(dcEntry
										.getPathString()));
