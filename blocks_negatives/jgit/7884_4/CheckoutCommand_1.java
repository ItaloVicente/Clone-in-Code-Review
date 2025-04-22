				while (startWalk.next()) {
					final ObjectId blobId = startWalk.getObjectId(0);
					final FileMode mode = startWalk.getFileMode(0);
					editor.add(new PathEdit(startWalk.getPathString()) {
						public void apply(DirCacheEntry ent) {
							if (checkoutIndex
									&& ent.getStage() > DirCacheEntry.STAGE_0) {
								UnmergedPathException e = new UnmergedPathException(ent);
								throw new JGitInternalException(e.getMessage(), e);
							}
							ent.setObjectId(blobId);
							ent.setFileMode(mode);
							File file = new File(workTree, ent.getPathString());
							File parentDir = file.getParentFile();
							try {
								FileUtils.mkdirs(parentDir, true);
								DirCacheCheckout.checkoutEntry(repo, file, ent, r);
							} catch (IOException e) {
								throw new JGitInternalException(
										MessageFormat.format(
												JGitText.get().checkoutConflictWithFile,
												ent.getPathString()), e);
							}
						}
					});
