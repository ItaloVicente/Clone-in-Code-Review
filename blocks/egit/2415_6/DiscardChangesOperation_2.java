		switch (type) {
		case INDEX:
			try {
				DirCacheEntry entry = dc.getEntry(resRelPath);
				if (entry != null) {
					File file = new File(res.getLocationURI());
					DirCacheCheckout.checkoutEntry(repository, file, entry);
				}
			} finally {
				dc.unlock();
			}
			break;
		case HEAD:
			File f = new File(res.getLocationURI());

			RevCommit commit = new RevWalk(repository).parseCommit(repository.getRef(
					Constants.HEAD).getObjectId());

			TreeWalk w = TreeWalk.forPath(repository, resRelPath, commit.getTree());

			if (w == null)
				return;	// git doesn't know such resource path

			final ObjectId blobId = w.getObjectId(0);
			ObjectLoader ol = repository.open(blobId);
			final long blobSize = ol.getSize();

			File parentDir = f.getParentFile();
			File tmpFile = File.createTempFile("._" + f.getName(), null, parentDir); //$NON-NLS-1$
			FileOutputStream channel = new FileOutputStream(tmpFile);
			try {
				ol.copyTo(channel);
			} finally {
				channel.close();
			}

			if (!tmpFile.renameTo(f)) {
				FileUtils.delete(f);
				if (!tmpFile.renameTo(f))
					throw new IOException(CoreText.ReplaceWithHeadOperation_WritingError + f.getPath());
			}

			try {
				DirCacheEditor dcEdit = dc.editor();

				dcEdit.add(new PathEdit(resRelPath) {
					public void apply(DirCacheEntry ent) {
						ent.setObjectId(blobId);
						ent.setLength(blobSize);
					}
				});
				dcEdit.commit();
			} catch (Exception e) {
				throw new IOException(CoreText.ReplaceWithHeadOperation_IndexError + f.getPath());
			} finally {
				dc.unlock();
