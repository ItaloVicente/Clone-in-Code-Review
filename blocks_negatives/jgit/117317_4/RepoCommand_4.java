					URI submodUrl = URI.create(nameUri);
					if (targetUri != null) {
						submodUrl = relativize(targetUri, submodUrl);
					}
					cfg.setString("submodule", path, "path", path); //$NON-NLS-1$ //$NON-NLS-2$
					cfg.setString("submodule", path, "url", submodUrl.toString()); //$NON-NLS-1$ //$NON-NLS-2$

					DirCacheEntry dcEntry = new DirCacheEntry(path);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(FileMode.GITLINK);
					builder.add(dcEntry);

					for (CopyFile copyfile : proj.getCopyFiles()) {
						byte[] src = callback.readFile(
								nameUri, proj.getRevision(), copyfile.src);
						objectId = inserter.insert(Constants.OBJ_BLOB, src);
						dcEntry = new DirCacheEntry(copyfile.dest);
