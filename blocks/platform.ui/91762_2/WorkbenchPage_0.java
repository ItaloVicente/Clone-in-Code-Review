		IContextService cs = legacyWindow.getService(IContextService.class);
		try {
			cs.deferUpdates(true);
			if (workbenchPart == null) {
				window.getContext().set(ISources.ACTIVE_PART_NAME, null);
				window.getContext().set(ISources.ACTIVE_PART_ID_NAME, null);
				window.getContext().set(ISources.ACTIVE_SITE_NAME, null);
			} else {
				window.getContext().set(ISources.ACTIVE_PART_NAME, workbenchPart);
				window.getContext().set(ISources.ACTIVE_PART_ID_NAME, workbenchPart.getSite().getId());
				window.getContext().set(ISources.ACTIVE_SITE_NAME, workbenchPart.getSite());
			}
		} finally {
			cs.deferUpdates(false);
