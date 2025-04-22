	private EventHandler perspSavedListener = new EventHandler() {
		public void handleEvent(Event event) {
			final MPerspective savedPersp = (MPerspective) event.getProperty(EventTags.ELEMENT);
			String cache = getTrimCache(savedPersp);
			minMaxAddon.getPersistedState().put(savedPersp.getElementId(), cache);
		}

		private String getTrimCache(MPerspective savedPersp) {
			MWindow topWin = modelService.getTopLevelWindowFor(savedPersp);
			String perspIdStr = '(' + savedPersp.getElementId() + ')';

			String cache = getWinCache(topWin, perspIdStr);
			for (MWindow dw : savedPersp.getWindows()) {
				cache += getWinCache(dw, perspIdStr);
			}

			return cache;
		}

		private String getWinCache(MWindow win, String perspIdStr) {

			List<MPartStack> stackList = modelService.findElements(win, null, MPartStack.class,
					null);
			for (MPartStack stack : stackList) {
				winStr += getStackTrimLoc(stack, perspIdStr);
			}
			return winStr;
		}

		private String getStackTrimLoc(MPartStack stack, String perspIdStr) {
			MUIElement tcElement = modelService.find(stack.getElementId() + perspIdStr, stackWin);
			if (tcElement == null)

			MTrimBar bar = (MTrimBar) ((MUIElement) tcElement.getParent());
			int sideVal = bar.getSide().getValue();
			int index = bar.getChildren().indexOf(tcElement);
		}
	};

	private EventHandler perspOpenedListener = new EventHandler() {
		public void handleEvent(Event event) {
			final MPerspective openedPersp = (MPerspective) event.getProperty(EventTags.ELEMENT);

			MWindow topWin = modelService.getTopLevelWindowFor(openedPersp);
			showMinimizedTrim(topWin);
			for (MWindow dw : openedPersp.getWindows()) {
				showMinimizedTrim(dw);
			}
		}

		private void showMinimizedTrim(MWindow win) {
			List<MPartStack> stackList = modelService.findElements(win, null, MPartStack.class,
					null);
			for (MPartStack stack : stackList) {
				if (stack.getTags().contains(IPresentationEngine.MINIMIZED)) {
					createTrim(stack);
				}
			}
		}
	};

