	private String getWinCache(MWindow win, String perspIdStr) {
		String winStr = ""; //$NON-NLS-1$

		List<MPartStack> stackList = modelService.findElements(win, null, MPartStack.class, null);
		for (MPartStack stack : stackList) {
			winStr += getStackTrimLoc(stack, perspIdStr);
		}
		return winStr;
	}

	private String getStackTrimLoc(MPartStack stack, String perspIdStr) {
		MWindow stackWin = modelService.getTopLevelWindowFor(stack);// getContainingWindow(stack);
		MUIElement tcElement = modelService.find(stack.getElementId() + perspIdStr, stackWin);
		if (tcElement == null)
			return ""; //$NON-NLS-1$

		MTrimBar bar = (MTrimBar) ((MUIElement) tcElement.getParent());
		int sideVal = bar.getSide().getValue();
		int index = bar.getChildren().indexOf(tcElement);
		return stack.getElementId() + ' ' + sideVal + ' ' + index + "#"; //$NON-NLS-1$
	}

	@Inject
	@Optional
	private void subscribeTopicPerspOpened(
			@UIEventTopic(UIEvents.UILifeCycle.PERSPECTIVE_OPENED) Event event) {
		final MPerspective openedPersp = (MPerspective) event.getProperty(EventTags.ELEMENT);

		MWindow topWin = modelService.getTopLevelWindowFor(openedPersp);
		showMinimizedTrim(topWin);
		for (MWindow dw : openedPersp.getWindows()) {
			showMinimizedTrim(dw);
		}
	}

	private void showMinimizedTrim(MWindow win) {
		List<MPartStack> stackList = modelService.findElements(win, null, MPartStack.class, null);
		for (MPartStack stack : stackList) {
			if (stack.getTags().contains(IPresentationEngine.MINIMIZED)) {
				createTrim(stack);
			}
		}
