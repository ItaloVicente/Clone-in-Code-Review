	private EventHandler widgetListener = new EventHandler() {
		public void handleEvent(Event event) {
			final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!(changedElement instanceof MPartStack) && !(changedElement instanceof MArea))
				return;

			final CTabFolder ctf = getCTFFor(changedElement);
			if (ctf == null)
				return;

			MUIElement stateElement = changedElement;
			if (changedElement instanceof MPartStack) {
				MPartStack stack = (MPartStack) changedElement;
				MArea area = getAreaFor(stack);
				if (area != null && !(area.getWidget() instanceof CTabFolder))
					stateElement = area.getCurSharedRef();
			} else if (changedElement instanceof MArea)
				stateElement = changedElement.getCurSharedRef();

			adjustCTFButtons(stateElement);

			ctf.addCTabFolder2Listener(CTFButtonListener);

			ctf.addMouseListener(CTFDblClickListener);
		}
	};

