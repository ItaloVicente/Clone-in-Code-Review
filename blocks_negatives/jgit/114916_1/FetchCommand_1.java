				FetchRecurseSubmodulesMode recurseMode = getRecurseMode(
						walk.getPath());

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
					configure(f);
					if (callback != null) {
						callback.fetchingSubmodule(walk.getPath());
