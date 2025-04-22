		if (blobId == null) {
			try {
				DirCache cache = db.readDirCache();
				blobId = locateBlobObjectId(cache);
				if (blobId != null) {
					metadata = getMetadata(cache);
				}
			} catch (IOException e) {
				throw new CoreException(Activator.error(
						NLS.bind(CoreText.IndexFileRevision_errorLookingUpPath,
								path),
						e));
			}
		}
		return new IndexBlobStorage(db, path, blobId, metadata);
