
		if (added.isEmpty() && changed.isEmpty() && removed.isEmpty()
				&& missing.isEmpty() && modified.isEmpty()
				&& untracked.isEmpty())
			return false;
		else
			return true;
