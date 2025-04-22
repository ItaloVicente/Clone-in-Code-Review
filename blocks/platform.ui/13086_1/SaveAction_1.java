			IWorkbenchPart activePart = getActivePart();
			WorkbenchPage workbenchPage;
			if (activePart != null) {
				workbenchPage = (WorkbenchPage) activePart.getSite().getPage();
			} else {
				workbenchPage = (WorkbenchPage) getActivePage();
			}
			workbenchPage.saveSaveable(saveView, activePart, false, false);
			return;
