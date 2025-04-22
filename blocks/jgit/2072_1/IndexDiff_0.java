		if (assumeUnchanged == null) {
			HashSet<String> unchanged = new HashSet<String>();
			for (int i = 0; i < dirCache.getEntryCount(); i++)
				if (dirCache.getEntry(i).isAssumeValid())
					unchanged.add(dirCache.getEntry(i).getPathString());
			assumeUnchanged = unchanged;
		}
