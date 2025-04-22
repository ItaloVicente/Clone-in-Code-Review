		this.directoriesToImport = new HashSet<>();
		for (File dir : potentialProjects.keySet()) {
			if (!isExistingProject(dir)) {
				directoriesToImport.add(dir);
			}
		}
		tree.setCheckedElements(directoriesToImport.toArray(new Object[directoriesToImport.size()]));
