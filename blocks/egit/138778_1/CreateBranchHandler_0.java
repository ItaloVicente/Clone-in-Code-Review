			IRepositoryCommit commit = commits.get(0);

			List<Ref> branches = getBranches(commit);

			String base;
			if (branches.isEmpty()) {
				base = commit.getRevCommit().getName();
			} else {
				Collections.sort(branches, new Comparator<Ref>() {

					@Override
					public int compare(Ref o1, Ref o2) {
						String refName1 = o1.getName();
						String refName2 = o2.getName();
						if (refName1.startsWith(Constants.R_REMOTES)) {
							if (refName2.startsWith(Constants.R_HEADS))
								return -1;
							else
								return CommonUtils.STRING_ASCENDING_COMPARATOR
										.compare(refName1, refName2);
						} else {
							if (refName2.startsWith(Constants.R_REMOTES))
								return 1;
							else
								return CommonUtils.STRING_ASCENDING_COMPARATOR
										.compare(refName1, refName2);
						}
					}
				});
				Ref branch = branches.get(0).getLeaf();
				base = branch.getName();

			}
