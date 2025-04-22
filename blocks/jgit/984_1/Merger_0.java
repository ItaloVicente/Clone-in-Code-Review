		try {
			return mergeImpl();
		} finally {
			if (inserter != null)
				inserter.release();
		}
