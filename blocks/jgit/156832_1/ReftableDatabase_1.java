	public Optional<Long> logEntryUpdateIndex(String refname
		lock.lock();
		try {
			Reftable r = reader();
			LogCursor c = r.seekLog(refname);
			while (index >= 0) {
				if (!c.next()) {
					 return Optional.empty();
				}
				index--;
			}

			return Optional.of(c.getUpdateIndex());
		} finally {
			lock.unlock();
		}
	}

