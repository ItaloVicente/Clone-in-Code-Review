	}

	protected boolean saveDirtyEditors() {
		return IDEWorkbenchPlugin.getDefault().getWorkbench().saveAllEditors(true);
	}

	@Override
