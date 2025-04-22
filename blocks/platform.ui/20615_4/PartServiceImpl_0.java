	private EventHandler minimizedPartHandler = new EventHandler() {
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

			MPart part = toPart(((MPartStack) element).getSelectedElement());
			if (part != null && minimizedTagAdded) {
				firePartHidden(part);
			} else if (part != null) {
				firePartVisible(part);
			}
		}

		private MPart toPart(MStackElement stackElement) {
			if (stackElement != null) {
				return stackElement instanceof MPlaceholder ? (MPart) ((MPlaceholder) stackElement)
						.getRef() : (MPart) stackElement;
			}
			return null;
		}
	};

