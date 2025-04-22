	private void checkoutIndex() throws TeamException {
		DirCache dc = null;
		try {
			dc = repository.lockDirCache();
			DirCacheCheckout checkout = new DirCacheCheckout(repository, dc,
					commit.getTree());
			checkout.setFailOnConflict(false);
			checkout.checkout();
		} catch (IOException e) {
			throw new TeamException(
					CoreText.ResetOperation_mappingTreeForCommit, e);
		} finally {
			if (dc != null)
				dc.unlock();
		}
