	private void restoreShowInMruPartIdsList() {
		String mruList = getWindowModel().getPersistedState().get(IWorkbenchConstants.TAG_SHOW_IN_TIME);
		if (mruList != null) {
			try {
				IMemento memento = XMLMemento.createReadRoot(new StringReader(mruList));
				IMemento[] mementoChildren = memento.getChildren();
				for (IMemento child : mementoChildren) {
					mruShowInPartIds.add(child.getID());
				}
			} catch (WorkbenchException e) {
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.ERROR,
								WorkbenchMessages.WorkbenchPage_problemRestoringTitle, e));
			}
		}
	}

