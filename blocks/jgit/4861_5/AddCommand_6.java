								FileMode mode = f.getEntryFileMode();
								entry.setFileMode(mode);

								if (FileMode.GITLINK != mode) {
									entry.setLength(sz);
									entry.setLastModified(f
											.getEntryLastModified());
									InputStream in = f.openEntryStream();
									try {
										entry.setObjectId(inserter.insert(
												Constants.OBJ_BLOB
									} finally {
										in.close();
									}
									builder.add(entry);
									lastAddedFile = path;
								} else {
									Repository subRepo = Git.open(
											new File(repo.getWorkTree()
											.getRepository();
									ObjectId subRepoHead = subRepo
											.resolve(Constants.HEAD);
									if (subRepoHead != null) {
										entry.setObjectId(subRepoHead);
										builder.add(entry);
										lastAddedFile = path;
									}
