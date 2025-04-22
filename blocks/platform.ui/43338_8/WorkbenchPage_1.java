	private void restoreShowInMruPartIdsList() {
		String mruList = getWindowModel().getPersistedState().get(IWorkbenchConstants.TAG_MRU_LIST);
		if (mruList != null) {
			try {
				IMemento memento = XMLMemento.createReadRoot(new StringReader(mruList));
				IMemento[] mementoChildren = memento.getChildren();
				for (IMemento child : mementoChildren) {
					mruPartIds.add(child.getID());
				}
			} catch (WorkbenchException e) {
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.ERROR,
								WorkbenchMessages.WorkbenchPage_problemRestoringTitle, e));
			}
		}
	}

