		stylingHandler = new EventHandler() {
			public void handleEvent(Event event) {
				MUIElement changed = (MUIElement) event
						.getProperty(UIEvents.EventTags.ELEMENT);
				if (!(changed instanceof MPart))
					return;

				MPart newActivePart = (MPart) changed;
				MUIElement partParent = newActivePart.getParent();
				if (partParent == null
						&& newActivePart.getCurSharedRef() != null)
					partParent = newActivePart.getCurSharedRef().getParent();

				MPartStack pStack = (MPartStack) (partParent instanceof MPartStack ? partParent
						: null);

				EModelService ms = newActivePart.getContext().get(
						EModelService.class);
				List<String> tags = new ArrayList<String>();
				tags.add(CSSConstants.CSS_ACTIVE_CLASS);
				List<MUIElement> activeElements = ms.findElements(
						ms.getTopLevelWindowFor(newActivePart), null,
						MUIElement.class, tags);
				for (MUIElement element : activeElements) {
					if (element instanceof MPartStack && element != pStack) {
						styleElement(element, false);
					} else if (element instanceof MPart
							&& element != newActivePart) {
						styleElement(element, false);
					}
				}

				if (pStack != null)
					styleElement(pStack, true);
				styleElement(newActivePart, true);
			}
		};
		eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, stylingHandler);

