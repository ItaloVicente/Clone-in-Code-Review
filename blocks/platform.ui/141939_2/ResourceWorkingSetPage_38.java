		findCheckedResources(resources, (IContainer) tree.getInput());
		if (workingSet == null) {
			IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
			workingSet = workingSetManager.createWorkingSet(getWorkingSetName(),
					resources.toArray(new IAdaptable[resources.size()]));
		} else {
			workingSet.setName(getWorkingSetName());
			workingSet.setElements(resources.toArray(new IAdaptable[resources.size()]));
		}
	}

	@Override
