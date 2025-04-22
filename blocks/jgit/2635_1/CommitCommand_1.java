				if (only != null && !only.isEmpty()) {
					DirCacheEditor editor = index.editor();

					DirCache onlyIndex = DirCache.newInCore();
					DirCacheBuilder builder = onlyIndex.builder();

					for (String s : only) {
						DirCacheEntry entry = index.getEntry(s);
						if (entry == null)
							throw new JGitInternalException(
									MessageFormat.format(
											JGitText.get().entryNotFoundByPath
											s));
						editor.add(new DeletePath(entry));
						builder.add(entry);
					}
					editor.commit();
					builder.finish();

					index = onlyIndex;
				}

