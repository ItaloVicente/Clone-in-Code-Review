	       	this.overwriteExistingResourcesCheckbox.setEnabled(this.resourcesRadio.getSelection());
	       	this.createTopLevelFolderCheckbox.setEnabled(this.resourcesRadio.getSelection());
	       	
	       	if (this.createLinksInWorkspaceButton != null) {
	       		this.createLinksInWorkspaceButton.setEnabled(this.resourcesRadio.getSelection());
				IPath path = getContainerFullPath();
		    	if (path != null && relativePathVariableGroup != null) {
					IResource target = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
					if (target != null && target.isVirtual())
						createVirtualFoldersButton.setSelection(true);
		    	}
				relativePathVariableGroup.setEnabled(createLinksInWorkspaceButton.getSelection());
				createVirtualFoldersButton.setEnabled(createLinksInWorkspaceButton.getSelection());
		
				if (!selectionGroup.isEveryItemChecked() ||
					(createTopLevelFolderCheckbox.getSelection())) {
		        	createVirtualFoldersButton.setSelection(true);
				}
	    	}
        }
