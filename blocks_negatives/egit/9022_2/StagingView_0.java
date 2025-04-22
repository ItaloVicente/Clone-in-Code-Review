			StagingEntry entry = (StagingEntry) iterator.next();
			switch (entry.getState()) {
			case ADDED:
			case CHANGED:
			case REMOVED:
				break;
			case CONFLICTING:
			case MODIFIED:
			case PARTIALLY_MODIFIED:
			case UNTRACKED:
				if (add == null)
					add = git.add();
				add.addFilepattern(entry.getPath());
				break;
			case MISSING:
				if (rm == null)
					rm = git.rm();
				rm.addFilepattern(entry.getPath());
				break;
