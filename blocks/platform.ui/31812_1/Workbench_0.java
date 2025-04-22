
		IShellProvider shellProvider;
		IRunnableContext runnableContext;
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		if (window == null && windows.length > 0) {
			window = windows[0];
		}
		if (window != null) {
			shellProvider = window;
			runnableContext = window;
		} else {
			shellProvider = new IShellProvider() {
				@Override
				public Shell getShell() {
					return null;
				}
			};
			runnableContext = new ProgressMonitorDialog(null);
		}

		return WorkbenchPage.saveAll(new ArrayList<ISaveablePart>(dirtyParts), confirm, closing,
				true, runnableContext, shellProvider);
