	public void addToIndex(IFile file) throws CoreException, IOException {
		DirCache dc = repository.lockDirCache();
		try {
			String repoPath = getRepoRelativePath(file.getLocation()
					.toOSString());
			InputStream fis = file.getContents();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[100];
			try {
				int size = fis.read(buffer);
				while (size > 0) {
					bos.write(buffer, 0, size);
					size = fis.read(buffer);
				}
			} finally {
				fis.close();
				bos.close();
			}

			final byte[] newContent = bos.toByteArray();
			dc.editor().add(new PathEdit(repoPath) {
				@Override
				public void apply(DirCacheEntry ent) {

					ent.setFileMode(FileMode.REGULAR_FILE);

					ObjectInserter inserter = repository.newObjectInserter();
					ent.setLength(newContent.length);
					ent.setLastModified(System.currentTimeMillis());
					InputStream in = new ByteArrayInputStream(newContent);
					try {
						ent.setObjectId(inserter.insert(Constants.OBJ_BLOB,
								newContent.length, in));
						inserter.flush();
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					} finally {
						try {
							in.close();
						} catch (IOException e) {
						}
					}

				}
			});
		} finally {
			dc.unlock();
		}
