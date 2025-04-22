	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.e4.ui.workbench.renderers.swt.SWTPartRenderer#processContents
	 * (org.eclipse.e4.ui.model.application.ui.MElementContainer)
	 */
	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		try {
			processedContent++;
			super.processContents(container);
		} finally {
			processedContent--;
			if (processedContent == 0) {
				forceLayout(container);
			}
		}
	}

