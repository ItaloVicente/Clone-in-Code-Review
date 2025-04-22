	private EventHandler minimalizedPartHandler = new EventHandler() {
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(element instanceof MPartStack)) {
				return;
			}

			Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);
			Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);

			boolean minimizedTagAdded = UIEvents.isADD(event)
					&& IPresentationEngine.MINIMIZED.equals(newValue);
			boolean minimizedTagRemoved = UIEvents.isREMOVE(event)
					&& IPresentationEngine.MINIMIZED.equals(oldValue);

			if (!(minimizedTagAdded || minimizedTagRemoved)) {
				return;
			}

			for (MStackElement stackElement : ((MPartStack) element).getChildren()) {
				MPart part = toPart(stackElement);
				if (part.isToBeRendered() && minimizedTagAdded) {
					firePartHidden(part);
				} else if (part.isToBeRendered()) {
					firePartVisible(part);
				}
			}
		}

		private MPart toPart(MStackElement stackElement) {
			return stackElement instanceof MPlaceholder ? (MPart) ((MPlaceholder) stackElement)
					.getRef() : (MPart) stackElement;
		}
	};

