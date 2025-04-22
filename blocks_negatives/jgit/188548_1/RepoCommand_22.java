					URI submodUrl = URI.create(url);
					if (targetUri != null) {
						submodUrl = relativize(targetUri, submodUrl);
					}
					cfg.setString("submodule", name, "path", path); //$NON-NLS-1$ //$NON-NLS-2$
					cfg.setString("submodule", name, "url", //$NON-NLS-1$ //$NON-NLS-2$
							submodUrl.toString());

					if (objectId != null) {
						DirCacheEntry dcEntry = new DirCacheEntry(path);
						dcEntry.setObjectId(objectId);
						dcEntry.setFileMode(FileMode.GITLINK);
						builder.add(dcEntry);

						for (CopyFile copyfile : proj.getCopyFiles()) {
							RemoteFile rf = callback.readFileWithMode(
								url, proj.getRevision(), copyfile.src);
							objectId = inserter.insert(Constants.OBJ_BLOB,
									rf.getContents());
							dcEntry = new DirCacheEntry(copyfile.dest);
							dcEntry.setObjectId(objectId);
							dcEntry.setFileMode(rf.getFileMode());
							builder.add(dcEntry);
						}
						for (LinkFile linkfile : proj.getLinkFiles()) {
							String link;
								link = FileUtils.relativizeGitPath(
									linkfile.dest.substring(0,
										linkfile.dest.lastIndexOf('/')),
							} else {
							}

							objectId = inserter.insert(Constants.OBJ_BLOB,
									link.getBytes(UTF_8));
							dcEntry = new DirCacheEntry(linkfile.dest);
							dcEntry.setObjectId(objectId);
							dcEntry.setFileMode(FileMode.SYMLINK);
							builder.add(dcEntry);
						}
					}
				}
				String content = cfg.toText();

				final DirCacheEntry dcEntry = new DirCacheEntry(Constants.DOT_GIT_MODULES);
				ObjectId objectId = inserter.insert(Constants.OBJ_BLOB,
						content.getBytes(UTF_8));
				dcEntry.setObjectId(objectId);
				dcEntry.setFileMode(FileMode.REGULAR_FILE);
				builder.add(dcEntry);

				if (recordSubmoduleLabels) {
					final DirCacheEntry dcEntryAttr = new DirCacheEntry(Constants.DOT_GIT_ATTRIBUTES);
					ObjectId attrId = inserter.insert(Constants.OBJ_BLOB,
							attributes.toString().getBytes(UTF_8));
					dcEntryAttr.setObjectId(attrId);
					dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
					builder.add(dcEntryAttr);
				}

				builder.finish();
