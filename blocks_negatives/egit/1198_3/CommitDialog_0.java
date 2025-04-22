
						IProject project = commitItem.file.getProject();
						RepositoryMapping map = RepositoryMapping.getMapping(project);

						Repository repo = map.getRepository();
						GitIndex index = null;
						index = repo.getIndex();
						String repoRelativePath = map.getRepoRelativePath(commitItem.file);
						Entry entry = index.getEntry(repoRelativePath);
						if (entry != null && entry.isModified(map.getWorkTree())) {
							entry.update(new File(map.getWorkTree(), entry.getName()));
							if (!changedIndexes.contains(index))
								changedIndexes.add(index);
							commitItem.status = UIText.CommitDialog_StatusModified;
						} else if (entry == null) {
							final Tree headTree = repo.mapTree(Constants.HEAD);
							TreeEntry  headEntry = (headTree == null ? null : headTree.findBlobMember(repoRelativePath));
							if (headEntry == null){
								entry = index.add(map.getWorkTree(), new File(map.getWorkTree(), repoRelativePath));
								if (!changedIndexes.contains(index))
									changedIndexes.add(index);
								commitItem.status = UIText.CommitDialog_StatusAdded;
							}
						}
