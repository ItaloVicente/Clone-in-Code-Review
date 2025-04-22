
	private void persistWorkbenchState() {
		try {
			XMLMemento memento = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_WORKBENCH);
			IStatus status = saveWorkbenchState(memento);

			if (status.getSeverity() == IStatus.OK) {
				StringWriter writer = new StringWriter();
				memento.save(writer);
				application.getPersistedState().put(MEMENTO_KEY, writer.toString());
			} else {
				WorkbenchPlugin.log(new Status(status.getSeverity(), PlatformUI.PLUGIN_ID,
						WorkbenchMessages.Workbench_problemsSavingMsg));
			}
		} catch (IOException e) {
			WorkbenchPlugin.log(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
					WorkbenchMessages.Workbench_problemsSavingMsg, e));
		}
	}

	private IStatus saveWorkbenchState(IMemento memento) {
		MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK,
				WorkbenchMessages.Workbench_problemsSaving, null);


		result.add(getEditorHistory().saveState(
				memento.createChild(IWorkbenchConstants.TAG_MRU_LIST)));
		return result;
	}

	private void restoreWorkbenchState() {
		try {
			String persistedState = application.getPersistedState().get(MEMENTO_KEY);
			if (persistedState != null) {
				XMLMemento memento = XMLMemento.createReadRoot(new StringReader(persistedState));
				IStatus status = readWorkbenchState(memento);

				if (status.getSeverity() != IStatus.OK) {
					WorkbenchPlugin.log(new Status(status.getSeverity(), PlatformUI.PLUGIN_ID,
							WorkbenchMessages.Workbench_problemsRestoring));
				}
			}
		} catch (Exception e) {
			WorkbenchPlugin.log(new Status(
					IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
					WorkbenchMessages.Workbench_problemsRestoring, e));
		}
	}

	private IStatus readWorkbenchState(IMemento memento) {
		MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK,
				WorkbenchMessages.Workbench_problemsRestoring, null);

		try {
			UIStats.start(UIStats.RESTORE_WORKBENCH, "MRUList"); //$NON-NLS-1$
			IMemento mruMemento = memento.getChild(IWorkbenchConstants.TAG_MRU_LIST);
			if (mruMemento != null) {
				result.add(getEditorHistory().restoreState(mruMemento));
			}
		} finally {
			UIStats.end(UIStats.RESTORE_WORKBENCH, this, "MRUList"); //$NON-NLS-1$
		}
		return result;
	}
