			if (!reuse) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().hideView((IViewPart) view);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(IHistoryView.VIEW_ID);
			}
