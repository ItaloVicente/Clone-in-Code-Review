		try {
			IHistoryView part = (IHistoryView) page.showView(
					IHistoryView.VIEW_ID, null, IWorkbenchPage.VIEW_VISIBLE);
			HistoryPageInput input = createHistoryPageInputWhenEditorOpened();
			part.showHistoryFor(input);
		} catch (PartInitException e) {
			Activator.handleError("Error displaying blame annotations", e, //$NON-NLS-1$
					false);
		}

