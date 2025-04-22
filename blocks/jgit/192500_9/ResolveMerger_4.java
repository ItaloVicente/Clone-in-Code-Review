	private void remove(byte[] path
		if (p != null && !p.getEntryFileMode().equals(FileMode.TREE)) {
			DirCacheEntry e = new DirCacheEntry(path
			builder.remove(e);
		}
	}

