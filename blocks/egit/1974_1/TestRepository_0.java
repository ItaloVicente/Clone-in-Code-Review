		DirCache dc = repository.lockDirCache();
		try {
			String repoPath = getRepoRelativePath(new Path(file.getPath())
					.toString());
			FileInputStream fis = new FileInputStream(file);
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
			DirCacheEditor editor = dc.editor();
			editor.add(new PathEdit(repoPath) {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.REGULAR_FILE);
					ent.setAssumeValid(false);
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
			try {
				editor.commit();
			} catch (RuntimeException e) {
				if (e.getCause() instanceof IOException)
					throw (IOException) e.getCause();
				else
					throw e;
			}
		} finally {
			dc.unlock();
		}
