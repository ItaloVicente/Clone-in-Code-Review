				if (objectId == null) {
					return rw.parseAny(repo.resolve(Constants.HEAD));

				} else {
					return rw.parseAny(objectId);
				}
			} finally {
				rw.release();
