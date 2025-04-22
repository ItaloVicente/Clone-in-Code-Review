	public boolean deleteLog(String refName
	{
		reftableDatabase.getLock().lock();
		try {
			Optional<Long> idx = reftableDatabase.logEntryUpdateIndex(refName
			if (!idx.isPresent()) {
				return  false;
			}
			long next = reftableDatabase.nextUpdateIndex();
			return addReftable(wr -> {
				wr.setMinUpdateIndex(next).setMaxUpdateIndex(next).begin().deleteLog(refName
			});
		} finally {
			reftableDatabase.getLock().unlock();
		}
	}

