		GitSynchronizeDataSet gsdSet = new GitSynchronizeDataSet();

		Map<Repository, String> branches = page.getSelectedBranches();
		for (Entry<Repository, String> branchesEntry : branches.entrySet())
			try {
				gsdSet.add(new GitSynchronizeData(branchesEntry.getKey(),
						Constants.HEAD, branchesEntry.getValue(), false));
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}

		Set<IProject> selectedProjects
				 = page.getSelectedProjects();
		GitModelSynchronize.launch(gsdSet, selectedProjects
				.toArray(new IResource[selectedProjects
				.size()]));
