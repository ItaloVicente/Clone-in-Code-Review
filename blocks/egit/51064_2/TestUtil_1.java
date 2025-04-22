				IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
						.getWorkbenchWindows();
				for (IWorkbenchWindow window : windows) {
					IWorkbenchPage workbenchPage = window.getActivePage();
					IViewReference[] views = workbenchPage.getViewReferences();
					for (int i = 0; i < views.length; i++) {
						IViewReference view = views[i];
						if (viewId.equals(view.getId())) {
							workbenchPage.hideView(view);
						}
