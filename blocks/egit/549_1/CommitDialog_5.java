							commitItem.status = UIText.CommitDialog_StatusModified;
						} else if (entry == null) {
							final Tree headTree = repo.mapTree(Constants.HEAD);
							TreeEntry  headEntry = (headTree == null ? null : headTree.findBlobMember(repoRelativePath));
							if (headEntry == null){
								entry = index.add(map.getWorkDir(), new File(map.getWorkDir(), repoRelativePath));
								if (!changedIndexes.contains(index))
									changedIndexes.add(index);
								commitItem.status = UIText.CommitDialog_StatusAdded;
							}
