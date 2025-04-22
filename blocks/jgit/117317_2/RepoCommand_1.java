							URI submodUrl = URI.create(nameUri);
							if (targetUri != null) {
								submodUrl = relativize(targetUri
							}
							cfg.setString("submodule"
							cfg.setString("submodule"
									submodUrl.toString());

							DirCacheEntry dcEntry = new DirCacheEntry(path);
							dcEntry.setObjectId(objectId);
							dcEntry.setFileMode(FileMode.GITLINK);
							builder.add(dcEntry);

							for (CopyFile copyfile : bareProj.getCopyFiles()) {
								byte[] src = callback.readFile(nameUri
										bareProj.getRevision()
								objectId = inserter.insert(Constants.OBJ_BLOB
										src);
								dcEntry = new DirCacheEntry(copyfile.dest);
								dcEntry.setObjectId(objectId);
								dcEntry.setFileMode(FileMode.REGULAR_FILE);
								builder.add(dcEntry);
							}
							for (LinkFile linkfile : bareProj.getLinkFiles()) {
								String link;
									link = FileUtils.relativizeGitPath(
											linkfile.dest.substring(0
													linkfile.dest
															.lastIndexOf('/'))
													+ linkfile.src);
								} else {
											+ linkfile.src;
								}

								objectId = inserter.insert(Constants.OBJ_BLOB
										link.getBytes(
												Constants.CHARACTER_ENCODING));
								dcEntry = new DirCacheEntry(linkfile.dest);
								dcEntry.setObjectId(objectId);
								dcEntry.setFileMode(FileMode.SYMLINK);
								builder.add(dcEntry);
							}
