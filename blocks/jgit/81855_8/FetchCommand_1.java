	private FetchRecurseSubmodulesMode getRecurseMode(String path
			Config config) {
		if (submoduleRecurseMode != null) {
			return submoduleRecurseMode;
		}

		FetchRecurseSubmodulesMode mode = config.getEnum(
				FetchRecurseSubmodulesMode.values()
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
		if (mode != null) {
			return mode;
		}

		return FetchRecurseSubmodulesMode.ON_DEMAND;
	}

	private boolean isRecurseSubmodules() {
		return submoduleRecurseMode != null
				&& submoduleRecurseMode != FetchRecurseSubmodulesMode.NO;
	}

	private void fetchSubmodules()
			throws org.eclipse.jgit.api.errors.TransportException
			GitAPIException
		Set<String> updated = null;
		try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repo)) {
			while (walk.next()) {
				if (walk.getModulesPath() == null
						|| walk.getConfigUrl() == null) {
					continue;
				}

				Repository submoduleRepo = walk.getRepository();
				if (submoduleRepo == null) {
					continue;
				}

				FetchRecurseSubmodulesMode recurseMode = getRecurseMode(
						walk.getPath()

				if (recurseMode == FetchRecurseSubmodulesMode.ON_DEMAND) {
					if (updated == null) {
						IndexDiff indexDiff = new IndexDiff(repo
								Constants.FETCH_HEAD
								new FileTreeIterator(repo));
						indexDiff.diff();
						updated = indexDiff.getChanged();
						updated.addAll(indexDiff.getAdded());
					}
				}

				if (recurseMode == FetchRecurseSubmodulesMode.YES
						|| (recurseMode == FetchRecurseSubmodulesMode.ON_DEMAND
								&& updated != null
								&& updated.contains(walk.getPath()))) {
					FetchCommand f = new FetchCommand(submoduleRepo)
							.setProgressMonitor(monitor)
							.setTagOpt(tagOption)
							.setCheckFetchedObjects(checkFetchedObjects)
							.setRemoveDeletedRefs(isRemoveDeletedRefs())
							.setThin(thin)
							.setRefSpecs(refSpecs)
							.setDryRun(dryRun)
							.setRecurseSubmodules(recurseMode);
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new InvalidConfigurationException(e.getMessage()
		}
	}

