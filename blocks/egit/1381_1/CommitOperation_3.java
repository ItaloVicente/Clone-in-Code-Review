			ObjectInserter inserter = repo.newObjectInserter();
			try {
				inserter.insert(commit);
				inserter.flush();
			} finally {
				inserter.release();
			}
