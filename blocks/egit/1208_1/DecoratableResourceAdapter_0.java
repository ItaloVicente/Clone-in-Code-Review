			final ContainerTreeIterator workspaceIterator = treeWalk.getTree(
					T_WORKSPACE, ContainerTreeIterator.class);
			if (workspaceIterator != null) {
				ResourceEntry resourceEntry = workspaceIterator
						.getResourceEntry();
				if (resource.equals(resourceEntry.getResource())
						&& workspaceIterator.isEntryIgnored()) {
					ignored = true;
					return false;
				}
			}
