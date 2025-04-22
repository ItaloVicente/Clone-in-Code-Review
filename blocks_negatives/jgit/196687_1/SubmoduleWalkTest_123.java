							@Override
							public void apply(DirCacheEntry ent) {
								ent.setFileMode(FileMode.GITLINK);
								ent.setObjectId(subId);
							}
						})
