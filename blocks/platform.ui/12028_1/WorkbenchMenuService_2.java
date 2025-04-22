			mToolBar.getTags().add(ContributionsAnalyzer.MC_TOOLBAR);
			String tag = "toolbar:" + location.getPath(); //$NON-NLS-1$
			mToolBar.getTags().add(tag);
			final MPart part = getPartToExtend();
			if (part != null) {
				addToolbar(part, mToolBar, part.getContext());
			} else {
				final IWorkbenchWindow window = getWindow();
				if (window instanceof WorkbenchWindow) {
					MWindow windowModel = ((WorkbenchWindow) window).getModel();
					addToolbar(windowModel, mToolBar, windowModel.getContext());
				}
			}
