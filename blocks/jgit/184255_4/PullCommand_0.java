				FetchCommand fetch = new FetchCommand(repo).setRemote(remote)
						.setRecurseSubmodules(submoduleRecurseMode)
						.setRefSpecs(remoteBranchName);
				configure(fetch);

				fetchRes = fetch.call();
				try {
					r = repo.getRefDatabase().findRef(Constants.FETCH_HEAD);
				} catch (IOException e) {
					throw new RefNotFoundException(
							MessageFormat.format(JGitText.get().refNotResolved
									Constants.FETCH_HEAD));
				}
				if (r == null) {
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
				}
