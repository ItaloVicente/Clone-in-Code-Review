		if (pathEditorInput != null)
			source = new File(pathEditorInput.getPath().toOSString());

		if (input instanceof IFileEditorInput) {
			if (resource == null)
				ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener);
			resource = ((IFileEditorInput)input).getFile();
		} else if (resource != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
			resource = null;
		}

		super.setInputWithNotify(input);
	}

	private static boolean usesStorageFiles(String progID) {
		return (progID != null && (progID.startsWith("Word.", 0) //$NON-NLS-1$
				|| progID.startsWith("MSGraph", 0) //$NON-NLS-1$
				|| progID.startsWith("PowerPoint", 0) //$NON-NLS-1$
		|| progID.startsWith("Excel", 0))); //$NON-NLS-1$
	}

	private void sourceChanged(IFile newFile) {

		FileEditorInput newInput = new FileEditorInput(newFile);
		setInputWithNotify(newInput);
		sourceChanged = true;
		setPartName(newInput.getName());

	}

	@Override
