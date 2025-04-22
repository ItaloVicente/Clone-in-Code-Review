			IProject parentProject = this.folder.getProject();
			Set<IWorkingSet> parentWorkingSets = new HashSet<IWorkingSet>();
			IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
			for (IWorkingSet workingSet : workingSetManager.getWorkingSets()) {
				for (IAdaptable element : workingSet.getElements()) {
					if (parentProject.equals(element.getAdapter(IProject.class))) {
						parentWorkingSets.add(workingSet);
						break;
					}
 				}
			}
