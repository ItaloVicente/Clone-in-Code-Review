		if (force || (!trustFolderStat)
				|| old.snapshot.isModified(packDirectory)) {
			PackList newList = scanPacks(old);
			return old != newList;
		}
		return false;
