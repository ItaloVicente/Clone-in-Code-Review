
			try (SubmoduleWalk generator = SubmoduleWalk.forIndex(repo)) {
				while (generator.next()) {
					if (generator.getModulesPath() == null) {
						continue;
					}
					String url = generator.getConfigUrl();
					if (url == null) {
						continue;
					}

					Repository submoduleRepo = generator.getRepository();
					if (submoduleRepo == null) {
						continue;
					}
					FetchRecurseSubmodulesMode recurseMode = submoduleRecurseMode == null
							? getRecurseMode(generator.getPath()
							: submoduleRecurseMode;
					if (recurseMode == null
							|| recurseMode == FetchRecurseSubmodulesMode.NO) {
						continue;
					} else if (recurseMode == FetchRecurseSubmodulesMode.YES) {
						FetchCommand f = new FetchCommand(submoduleRepo)
								.setProgressMonitor(monitor)
								.setTagOpt(tagOption)
								.setCheckFetchedObjects(checkFetchedObjects)
								.setRemoveDeletedRefs(isRemoveDeletedRefs())
								.setThin(thin)
								.setRefSpecs(refSpecs)
								.setDryRun(dryRun);
					}
				}
			} catch (IOException e) {
				throw new JGitInternalException(e.getMessage()
			} catch (ConfigInvalidException e) {
				throw new InvalidConfigurationException(e.getMessage()
			}

