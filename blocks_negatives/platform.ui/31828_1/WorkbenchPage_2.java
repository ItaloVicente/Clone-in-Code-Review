		List modelsToSave;
		if (confirm) {
			boolean saveable2Processed = false;
			ListIterator listIterator = dirtyParts.listIterator();

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
								currentPage
										.setPerspective(currentPageOriginalPerspective.getDesc());
							}
						}
						currentPage = page;
						currentPageOriginalPerspective = page.getActivePerspective();
					}
					if (confirm) {
						page.bringToTop(part);
					}
					int choice = SaveableHelper.savePart((ISaveablePart2) part,
							page.getWorkbenchWindow(), confirm);
					if (choice == ISaveablePart2.CANCEL) {
						return false;
					} else if (choice != ISaveablePart2.DEFAULT) {
						saveable2Processed = true;
						listIterator.remove();
					}
				}
			}

			if (currentPage != null && currentPageOriginalPerspective != null) {
				if (!currentPageOriginalPerspective.equals(currentPage.getActivePerspective())) {
					currentPage.setPerspective(currentPageOriginalPerspective.getDesc());
				}
			}

			if (saveable2Processed) {
				listIterator = dirtyParts.listIterator();
				while (listIterator.hasNext()) {
					ISaveablePart part = (ISaveablePart) listIterator.next();
					if (!part.isDirty()) {
						listIterator.remove();
					}
				}
			}

