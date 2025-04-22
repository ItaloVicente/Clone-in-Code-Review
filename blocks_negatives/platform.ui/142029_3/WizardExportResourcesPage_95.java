		                resourceList.add(resource);
		                selectionMap.put(parent, resourceList);
		            }
		        } else {
					setupSelectionsBasedOnSelectedTypes(selectionMap,
		                    (IContainer) resource);
