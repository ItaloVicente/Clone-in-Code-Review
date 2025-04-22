			return !saveablesList.saveModels(modelsToSave, workbenchWindow, runnableContext,
					closing);

		}
		boolean saveable2Processed = false;
		ListIterator<ISaveablePart> listIterator = dirtyParts.listIterator();

		WorkbenchPage currentPage = null;
		Perspective currentPageOriginalPerspective = null;
		while (listIterator.hasNext()) {
			IWorkbenchPart part = (IWorkbenchPart) listIterator.next();
			if (part instanceof ISaveablePart2) {
				WorkbenchPage page = (WorkbenchPage) part.getSite().getPage();
				if (!Util.equals(currentPage, page)) {
					if (currentPage != null && currentPageOriginalPerspective != null) {
						if (!currentPageOriginalPerspective.equals(currentPage
								.getActivePerspective())) {
							currentPage.setPerspective(currentPageOriginalPerspective.getDesc());
						}
					}
					currentPage = page;
					currentPageOriginalPerspective = page.getActivePerspective();
