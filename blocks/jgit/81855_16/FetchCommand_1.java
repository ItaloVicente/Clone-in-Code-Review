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

	private void fetchSubmodules(FetchResult results)
			throws org.eclipse.jgit.api.errors.TransportException
			GitAPIException
		try (SubmoduleWalk walk = new SubmoduleWalk(repo);
				RevWalk revWalk = new RevWalk(repo)) {
			walk.setTree(revWalk.parseTree(repo.resolve(Constants.FETCH_HEAD)));
			while (walk.next()) {
				Repository submoduleRepo = walk.getRepository();

				if (submoduleRepo == null || walk.getModulesPath() == null
						|| walk.getConfigUrl() == null) {
					continue;
				}

				FetchRecurseSubmodulesMode recurseMode = getRecurseMode(
						walk.getPath()

				if ((recurseMode == FetchRecurseSubmodulesMode.ON_DEMAND
						&& !submoduleRepo.hasObject(walk.getObjectId()))
						|| recurseMode == FetchRecurseSubmodulesMode.YES) {
					FetchCommand f = new FetchCommand(submoduleRepo)
							.setProgressMonitor(monitor).setTagOpt(tagOption)
							.setCheckFetchedObjects(checkFetchedObjects)
							.setRemoveDeletedRefs(isRemoveDeletedRefs())
							.setThin(thin).setRefSpecs(refSpecs)
							.setDryRun(dryRun)
							.setRecurseSubmodules(recurseMode);
					results.addSubmodule(walk.getPath()
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new InvalidConfigurationException(e.getMessage()
		}
	}

