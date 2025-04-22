			ObjectLoader loader = db.open(blobId, Constants.OBJ_BLOB);
			final InputStream objectInputStream = LfsHelper
					.getSmudgeFiltered(db, loader,
							LfsHelper.getAttributesForPath(db, path)
									.get(Constants.ATTR_DIFF))
					.openStream();
