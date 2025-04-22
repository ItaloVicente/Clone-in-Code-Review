					for (LinkFile linkfile : proj.getLinkFiles()) {
						String link;
							link = FileUtils.relativize(
									linkfile.dest.substring(0
											linkfile.dest.lastIndexOf('/'))
						} else {
						}

						objectId = inserter.insert(Constants.OBJ_BLOB
								link.getBytes(
										Constants.CHARACTER_ENCODING));
						dcEntry = new DirCacheEntry(linkfile.dest);
						dcEntry.setObjectId(objectId);
						dcEntry.setFileMode(FileMode.SYMLINK);
						builder.add(dcEntry);
					}
