		for (Object object : IDE.computeSelectedResources(this.initialResourceSelection)) {
			if (object instanceof IResource) {
				IResource currentResource = (IResource) object;
				if (currentResource.getType() == IResource.FILE) {
					this.resourceGroup.initialCheckListItem(currentResource);
				} else {
					this.resourceGroup.initialCheckTreeItem(currentResource);
				}
