	private void finishContextMenu() {
		popupMgr.add(new Separator());
		popupMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		getSite().registerContextMenu(POPUP_ID, popupMgr,
				revObjectSelectionProvider);
		getHistoryPageSite().getPart().getSite().setSelectionProvider(
				revObjectSelectionProvider);
	}

