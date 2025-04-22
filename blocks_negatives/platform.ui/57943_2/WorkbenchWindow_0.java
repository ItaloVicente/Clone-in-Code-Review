		if (changeDetected) {
			IMenuService ms = getWorkbench().getService(IMenuService.class);
			if (ms instanceof WorkbenchMenuService) {
				((WorkbenchMenuService) ms).updateManagers();
			}
		}
