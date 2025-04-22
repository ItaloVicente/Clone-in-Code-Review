		}
	}

	private void setupSelectionsBasedOnSelectedTypes() {

		Runnable runnable = () -> {
			Map selectionMap = new Hashtable();
			Iterator resourceIterator = resourceGroup.getAllWhiteCheckedItems().iterator();
			while (resourceIterator.hasNext()) {
				IResource resource = (IResource) resourceIterator.next();
				if (resource.getType() == IResource.FILE) {
					if (hasExportableExtension(resource.getName())) {
						List resourceList = new ArrayList();
						IContainer parent = resource.getParent();
						if (selectionMap.containsKey(parent)) {
