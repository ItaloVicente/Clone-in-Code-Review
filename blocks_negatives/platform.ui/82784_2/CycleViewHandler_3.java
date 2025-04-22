		item.setData(part);
	}

	private Image getImage(WorkbenchPage page, MPart part) {
		Object renderer = part.getRenderer();
		if (renderer instanceof SWTPartRenderer) {
			SWTPartRenderer partRenderer = (SWTPartRenderer) renderer;
			return partRenderer.getImage(part);
		}
		WorkbenchWindow wbw = (WorkbenchWindow) page.getWorkbenchWindow();
		if (wbw.getModel().getRenderer() instanceof SWTPartRenderer) {
			SWTPartRenderer partRenderer = (SWTPartRenderer) wbw.getModel().getRenderer();
			return partRenderer.getImage(part);
		}

		return null;
