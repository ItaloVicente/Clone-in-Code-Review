        enableButtonGroup(ensureSourceIsValid());

    	if (createLinksInWorkspaceButton != null) {
			IPath path = getContainerFullPath();
	    	if (path != null && relativePathVariableGroup != null) {
				IResource target = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
				if (target != null && target.isVirtual())
					createVirtualFoldersButton.setSelection(true);
	    	}
			relativePathVariableGroup.setEnabled(createLinksInWorkspaceButton.getSelection());
			createVirtualFoldersButton.setEnabled(createLinksInWorkspaceButton.getSelection());
