	BlobStorage(final Repository repository, final String fileName,
			final ObjectId blob) {
		db = repository;
		path = fileName;
		blobId = blob;
		repositoryUtil = Activator.getDefault().getRepositoryUtil();
