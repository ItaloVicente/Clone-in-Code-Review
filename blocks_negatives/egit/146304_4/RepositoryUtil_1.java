					List<Ref> remoteBranches = repository.getRefDatabase()
							.getRefsByPrefix(Constants.R_REMOTES);
					for (Ref branch : remoteBranches) {
						ObjectId objectId = branch.getObjectId();
						if (objectId != null
								&& objectId.name().equals(commitId)) {
							branchNames.add(branch.getName());
						}
					}
					if (!branchNames.isEmpty()) {
						cacheValue = branchNames.toArray(new String[branchNames
								.size()])[branchNames.size() - 1];
					}
