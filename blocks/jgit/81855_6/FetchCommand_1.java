	private FetchRecurseSubmodulesMode getRecurseMode(String path
			Config config) {
		return config.getEnum(FetchRecurseSubmodulesMode.values()
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				path
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
				null);
	}

	private boolean isRecurseSubmodules() {
		return submoduleRecurseMode != null
				&& submoduleRecurseMode == FetchRecurseSubmodulesMode.YES;
	}

	private void fetchSubmodules()
			throws org.eclipse.jgit.api.errors.TransportException
			GitAPIException
		try (SubmoduleWalk generator = SubmoduleWalk.forIndex(repo)) {
			while (generator.next()) {
				if (generator.getModulesPath() == null
						|| generator.getConfigUrl() == null) {
					continue;
				}

				Repository submoduleRepo = generator.getRepository();
				if (submoduleRepo == null) {
					continue;
				}

				FetchRecurseSubmodulesMode recurseMode = submoduleRecurseMode == null
						? getRecurseMode(generator.getPath()
								submoduleRepo.getConfig())
						: submoduleRecurseMode;
				if (recurseMode == null) {
					continue;
				}
				switch (recurseMode) {
				case YES:
					FetchCommand f = new FetchCommand(submoduleRepo)
							.setProgressMonitor(monitor)
							.setTagOpt(tagOption)
							.setCheckFetchedObjects(checkFetchedObjects)
							.setRemoveDeletedRefs(isRemoveDeletedRefs())
							.setThin(thin)
							.setRefSpecs(refSpecs)
							.setDryRun(dryRun)
							.setRecurseSubmodules(recurseMode);
					break;
				case ON_DEMAND:
					break;
				case NO:
					break;
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new InvalidConfigurationException(e.getMessage()
		}
	}

