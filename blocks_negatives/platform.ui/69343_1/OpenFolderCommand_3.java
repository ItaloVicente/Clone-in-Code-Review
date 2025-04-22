		final File directory = new File(res);
		wizard.setInitialImportSource(directory);
		final Path asPath = new Path(directory.getAbsolutePath());
		IProject parentProject = null;
		for (IProject project : workspaceRoot.getProjects()) {
			if (project.getLocation().isPrefixOf(asPath) && (parentProject == null || parentProject.getLocation().isPrefixOf(project.getLocation())) ) {
				parentProject = project;
			}
		}
		Set<IWorkingSet> initialWorkingSets = new HashSet<>();
		if (parentProject != null) {
			for (IWorkingSet workingSet : workbench.getWorkingSetManager().getAllWorkingSets()) {
				for (IAdaptable element : workingSet.getElements()) {
					if (element.equals(parentProject)) {
						initialWorkingSets.add(workingSet);
					}
				}
			}
		}
		if (initialWorkingSets.isEmpty()) {
			wizard.init(workbench, structuredSel);
		} else {
			wizard.setInitialWorkingSets(initialWorkingSets);
		}
