					Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
					MPart part = getModel();
					if (element == part) {
						if (part.getContext() != null) {
							part.getContext().set(getClass().getName(), this);
							unsubscribe();
						}
					}
