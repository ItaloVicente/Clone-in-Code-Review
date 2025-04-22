			ObjectLoader loader = db.open(blobId, Constants.OBJ_BLOB);


			final InputStream objectInputStream = LfsFactory.getInstance()
					.applySmudgeFilter(db, loader,
							null /* always try to load LFS blobs */)
					.openStream();
