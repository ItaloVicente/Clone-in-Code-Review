
	static class AlternateHandle {
		final FileObjectDatabase db;

		AlternateHandle(FileObjectDatabase db) {
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
