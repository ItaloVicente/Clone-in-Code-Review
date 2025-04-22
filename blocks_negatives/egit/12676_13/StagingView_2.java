				switch (entry.getState()) {
				case ADDED:
				case CHANGED:
				case REMOVED:
					break;
				case CONFLICTING:
				case MODIFIED:
				case PARTIALLY_MODIFIED:
				case UNTRACKED:
					addPaths.add(entry.getPath());
					break;
				case MISSING:
				case MISSING_AND_CHANGED:
					if (rm == null)
						rm = git.rm().setCached(true);
					rm.addFilepattern(entry.getPath());
					break;
				}
