		showInHistory = new Action(
				UIText.CommitFileDiffViewer_ShowInHistoryLabel, UIIcons.HISTORY) {
			@Override
			public void run() {
				ShowInContext context = getShowInContext();
				if (context == null)
					return;

				IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				IWorkbenchPart part = page.getActivePart();
				if (part instanceof IHistoryView) {
					((IShowInTarget) part).show(context);
				}
			}
		};

