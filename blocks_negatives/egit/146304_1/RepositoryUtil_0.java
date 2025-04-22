					List<Ref> remoteBranches = repository.getRefDatabase()
							.getRefsByPrefix(Constants.R_HEADS);
					for (Ref branch : remoteBranches) {
						ObjectId objectId = branch.getObjectId();
						if (objectId != null
								&& objectId.name().equals(commitId)) {
							branchNames.add(branch.getName());
						}
					}
