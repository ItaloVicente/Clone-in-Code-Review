	private void resetIndex() throws TeamException {
		DirCache dc = null;
		try {
			dc = repository.lockDirCache();
			dc.clear();
			DirCacheBuilder dcb = dc.builder();
			dcb.addTree(new byte[0], 0, repository.newObjectReader(), commit
					.getTree());
			dcb.commit();
		} catch (IOException e) {
			throw new TeamException(CoreText.ResetOperation_readingIndex, e);
		} finally {
			if (dc != null)
				dc.unlock();
		}
	}
