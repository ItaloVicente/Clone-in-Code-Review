			final MPart part = getPartToExtend();
			if (part != null) {
				addMenu(part, mMenu, part.getContext());
			} else {
				final IWorkbenchWindow window = getWindow();
				if (window instanceof WorkbenchWindow) {
					MWindow windowModel = ((WorkbenchWindow) window).getModel();
					addMenu(windowModel, mMenu, windowModel.getContext());
				}
			}
