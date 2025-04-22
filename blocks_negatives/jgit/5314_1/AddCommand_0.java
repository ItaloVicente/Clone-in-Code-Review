									builder.add(entry);
									lastAddedFile = path;
								} else {
									Repository subRepo = Git.open(
											new File(repo.getWorkTree(), path))
											.getRepository();
									ObjectId subRepoHead = subRepo
											.resolve(Constants.HEAD);
									if (subRepoHead != null) {
										entry.setObjectId(subRepoHead);
										builder.add(entry);
										lastAddedFile = path;
									}
								}
