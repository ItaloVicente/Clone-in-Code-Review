				FetchCommand fetch = new FetchCommand(repo).setRemote(remote)
						.setProgressMonitor(monitor).setTagOpt(tagOption)
						.setRecurseSubmodules(submoduleRecurseMode)
						.setRefSpecs(remoteBranchName);
				configure(fetch);

				fetchRes = fetch.call();
				r = fetchRes.getFetchedRef(remoteBranchName);
				if (r == null) {
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
				}
