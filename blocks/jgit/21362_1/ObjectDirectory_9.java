	static class AlternateHandle {
		final ObjectDirectory db;

		AlternateHandle(ObjectDirectory db) {
			this.db = db;
		}

		void close() {
			db.close();
		}
	}

	static class AlternateRepository extends AlternateHandle {
		final FileRepository repository;

		AlternateRepository(FileRepository r) {
			super(r.getObjectDatabase());
			repository = r;
		}

		void close() {
			repository.close();
		}
	}

