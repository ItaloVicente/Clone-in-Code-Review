		lock.lock();
		try {
			Reftable table = reader();

			int lastSlash = refName.lastIndexOf('/');
			while (0 < lastSlash) {
				if (table.hasRef(refName.substring(0, lastSlash))) {
					return true;
				}
				lastSlash = refName.lastIndexOf('/', lastSlash - 1);
			}

			return table.hasRefsWithPrefix(refName + '/');
		} finally {
			lock.unlock();
		}
