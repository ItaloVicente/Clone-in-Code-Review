			IProject parentProject = this.folder.getProject();
			Set<IWorkingSet> parentWorkingSets = new HashSet<IWorkingSet>();
			IWorkingSetManager workingSetManager = viewer.getCommonNavigator().getViewSite().getWorkbenchWindow().getWorkbench().getWorkingSetManager();
			for (IWorkingSet workingSet : workingSetManager.getWorkingSets()) {
				boolean currentWorkingSetIncludesParent = false;
				for (IAdaptable element : workingSet.getElements()) {
					if (parentProject.equals(element.getAdapter(IProject.class))) {
						currentWorkingSetIncludesParent = true;
					}
					if (currentWorkingSetIncludesParent) {
						parentWorkingSets.add(workingSet);
					}
 				}
			}
