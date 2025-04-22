			MPlaceholder placeholder = null;
			Object selected = event.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (selected instanceof MPlaceholder) {
				placeholder = (MPlaceholder) selected;
				selected = placeholder.getRef();
			}
