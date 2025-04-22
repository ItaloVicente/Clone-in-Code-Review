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
		try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repo)) {
			while (walk.next()) {
				Repository submoduleRepo = walk.getRepository();

				if (submoduleRepo == null || walk.getModulesPath() == null
						|| walk.getConfigUrl() == null) {
					continue;
				}

				FetchRecurseSubmodulesMode recurseMode = getRecurseMode(
						walk.getPath()
				boolean recurse = false;
				if (recurseMode == FetchRecurseSubmodulesMode.YES) {
					recurse = true;
				} else if (recurseMode == FetchRecurseSubmodulesMode.ON_DEMAND) {
					IndexDiff indexDiff = new IndexDiff(repo
							Constants.FETCH_HEAD
					indexDiff.setFilter(
							PathFilterGroup.createFromStrings(walk.getPath()));
					indexDiff.diff();
					recurse = indexDiff.getChanged().contains(walk.getPath());
				}

				if (recurse) {
					FetchCommand f = new FetchCommand(submoduleRepo)
							.setProgressMonitor(monitor)
							.setTagOpt(tagOption)
							.setCheckFetchedObjects(checkFetchedObjects)
							.setRemoveDeletedRefs(isRemoveDeletedRefs())
							.setThin(thin)
							.setRefSpecs(refSpecs)
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

