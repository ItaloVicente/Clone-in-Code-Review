		if (adaptable[0] == null) {
			WorkbenchPlugin.log("Unable to restore working set - cannot instantiate working set: " + factoryID); //$NON-NLS-1$
			return null;
		}
		if ((adaptable[0] instanceof IWorkingSet) == false) {
			WorkbenchPlugin.log("Unable to restore working set - element is not an IWorkingSet: " + factoryID); //$NON-NLS-1$
			return null;
		}
		return (IWorkingSet) adaptable[0];
	}

	protected void saveMruList(IMemento memento) {
		Iterator iterator = recentWorkingSets.iterator();

		while (iterator.hasNext()) {
			IWorkingSet workingSet = (IWorkingSet) iterator.next();
			IMemento mruMemento = memento.createChild(IWorkbenchConstants.TAG_MRU_LIST);

			mruMemento.putString(IWorkbenchConstants.TAG_NAME, workingSet.getName());
		}
	}

	protected void restoreMruList(IMemento memento) {
		IMemento[] mruWorkingSets = memento.getChildren(IWorkbenchConstants.TAG_MRU_LIST);

		for (int i = mruWorkingSets.length - 1; i >= 0; i--) {
			String workingSetName = mruWorkingSets[i].getString(IWorkbenchConstants.TAG_NAME);
			if (workingSetName != null) {
				IWorkingSet workingSet = getWorkingSet(workingSetName);
				if (workingSet != null) {
					internalAddRecentWorkingSet(workingSet);
				}
			}
		}
	}


	@Override
	public IWorkingSetEditWizard createWorkingSetEditWizard(IWorkingSet workingSet) {
		String editPageId = workingSet.getId();
		WorkingSetRegistry registry = WorkbenchPlugin.getDefault().getWorkingSetRegistry();
		IWorkingSetPage editPage = null;

		if (editPageId != null) {
			editPage = registry.getWorkingSetPage(editPageId);
		}

