			IViewPart part = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().findView(
							IHistoryView.VIEW_ID);
			boolean reuse = false;
			if (part != null) {
				if (((IHistoryView) part).getHistoryPage() instanceof GitHistoryPage)
					reuse = true;
			}
