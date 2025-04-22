			checker = new ReadOnlyStateChecker(shell, CHECK_MOVE_TITLE, CHECK_DELETE_MESSAGE);
			resources = checker.checkReadOnlyResources(resources);
			for (IResource resource : resources) {
				try {
					resource.delete(IResource.KEEP_HISTORY | IResource.FORCE, null);
				} catch (CoreException e) {
					StatusManager.getManager().handle(e, IDEWorkbenchPlugin.IDE_WORKBENCH);
				}
			}
		} else if (event.detail == DND.DROP_TARGET_MOVE) {
			IResource[] resources = getSelectedResources(typeMask);

			if (resources == null) {
