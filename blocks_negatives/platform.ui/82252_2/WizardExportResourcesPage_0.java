        Iterator it = this.initialResourceSelection.iterator();
        while (it.hasNext()) {
            IResource currentResource = (IResource) it.next();
            if (currentResource.getType() == IResource.FILE) {
				this.resourceGroup.initialCheckListItem(currentResource);
			} else {
				this.resourceGroup.initialCheckTreeItem(currentResource);
