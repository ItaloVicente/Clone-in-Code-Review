			WorkbenchPage page = (WorkbenchPage) activeWorkbenchWindow.getActivePage();
			Perspective activePerspective = page.getActivePerspective();
			CustomizePerspectiveDialog dlg = activeWorkbenchWindow.createCustomizePerspectiveDialog(activePerspective,
					mWindow.getContext());
			if (dlg.open() == Window.OK) {
				activeWorkbenchWindow.updateActionSets();
				activeWorkbenchWindow.firePerspectiveChanged(page, page.getPerspective(), IWorkbenchPage.CHANGE_RESET);
				activeWorkbenchWindow.firePerspectiveChanged(page, page.getPerspective(), IWorkbenchPage.CHANGE_RESET);
