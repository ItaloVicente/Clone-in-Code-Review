				RevCommit commit = rw.lookupCommit(id.toObjectId());
				return new IRepositoryCommit() {

					@Override
					public Repository getRepository() {
						return repo;
					}

					@Override
					public RevCommit getRevCommit() {
						return commit;
					}
				};
