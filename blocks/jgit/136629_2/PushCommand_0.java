				PushMode pushMode = getDefaultPushMode();

				String branch = repo.getFullBranch();
				BranchConfig branchConfig = new BranchConfig(repo.getConfig()
						repo.getBranch());

				String fetchRemote = getRemote();
				if (fetchRemote == null)
					fetchRemote = Constants.DEFAULT_REMOTE_NAME;
				boolean triangular = !remote.equals(fetchRemote);

				switch (pushMode) {
				case NOTHING:
					throw new InvalidRefNameException(
							JGitText.get().pushModeNoRefspec);
				case MATCHING:
					setPushAll();
					break;
				case CURRENT:
					if (branch != null)
						refSpecs.add(new RefSpec(branch));
					break;
				case TRACKING:
				case UPSTREAM:
					if (triangular)
						throw new InvalidRefNameException(
								MessageFormat.format(JGitText
										.get().pushModeInvalidRemote
										remote
					if (branch != null) {
						String merge = branchConfig.getMerge();
						if (merge == null)
							throw new InvalidRefNameException(
									MessageFormat.format(
											JGitText.get().pushModeNoUpstream
											branch));
					}
					break;
				case SIMPLE:
					if (branch != null) {
						if (!triangular) {
							String merge = branchConfig.getMerge();
							if (merge == null)
								throw new InvalidRefNameException(
										MessageFormat.format(JGitText
												.get().pushModeNoUpstream
												branch));
							if (!branch.equals(merge)) {
								throw new InvalidRefNameException(
								MessageFormat.format(JGitText
										.get().pushModeNotMatchingBranches
												merge
							}
						}
						refSpecs.add(new RefSpec(branch));
					}
					break;
				}

