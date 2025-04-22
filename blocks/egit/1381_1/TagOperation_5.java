			ObjectInserter inserter = repo.newObjectInserter();
			try {
				inserter.insert(tag);
				inserter.flush();
			} finally {
				inserter.release();
			}
