				if (ent.getStage() != DirCacheEntry.STAGE_0) {
					ent = new DirCacheEntry(e.path);
					e.apply(ent);
					if (ent.getRawMode() == 0)
						throw new IllegalArgumentException(MessageFormat
								.format(JGitText.get().fileModeNotSetForPath
										ent.getPathString()));
					lastIdx = cache.nextEntry(eIdx);
				} else {
					e.apply(ent);
				}
