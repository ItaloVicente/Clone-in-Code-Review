					continue;
				} else if (c == null && update) {
					continue;
				}

				DirCacheEntry entry = c != null ? c.getDirCacheEntry() : null;
				if (entry != null && entry.getStage() > 0
						&& lastAdded != null
						&& lastAdded.length == tw.getPathLength()
						&& tw.isPathPrefix(lastAdded
					continue;
				}

				if (tw.isSubtree() && !tw.isDirectoryFileConflict()) {
					tw.enterSubtree();
					continue;
				}

					if (entry != null
							&& (!update || GITLINK == entry.getFileMode())) {
						builder.add(entry);
					}
					continue;
				}

				if (entry != null && entry.isAssumeValid()) {
					builder.add(entry);
					continue;
				}

				if (f.getEntryRawMode() == TYPE_TREE) {
					tw.enterSubtree();
					continue;
				}

				byte[] path = tw.getRawPath();
				if (entry == null || entry.getStage() > 0) {
					entry = new DirCacheEntry(path);
