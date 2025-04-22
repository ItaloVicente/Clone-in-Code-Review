        Runnable runnable = new Runnable() {
            @Override
			public void run() {
                Map selectionMap = new Hashtable();
                Iterator resourceIterator = resourceGroup
                        .getAllWhiteCheckedItems().iterator();
                while (resourceIterator.hasNext()) {
                    IResource resource = (IResource) resourceIterator.next();
                    if (resource.getType() == IResource.FILE) {
                        if (hasExportableExtension(resource.getName())) {
                            List resourceList = new ArrayList();
                            IContainer parent = resource.getParent();
                            if (selectionMap.containsKey(parent)) {
								resourceList = (List) selectionMap.get(parent);
							}
                            resourceList.add(resource);
                            selectionMap.put(parent, resourceList);
                        }
                    } else {
						setupSelectionsBasedOnSelectedTypes(selectionMap,
                                (IContainer) resource);
					}
                }
                resourceGroup.updateSelections(selectionMap);
            }
        };
