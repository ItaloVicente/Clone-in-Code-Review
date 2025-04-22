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

