		MPart activeMPart = null;
		EPartService partService = null;
		try {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			partService = ((WorkbenchWindow) window).getModel().getContext().get(EPartService.class);
			activeMPart = getActivePart(window);
		} catch (Exception e1) {
		}

