
	@SuppressWarnings("javadoc")
	public class TabStateHandler implements EventHandler {
		public void handleEvent(Event event) {
			MUIElement element = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);
			Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);

			if (!validateElement(element)
					|| !validateValues(oldValue, newValue)) {
				return;
			}

			MPart part = newValue instanceof MPlaceholder ? (MPart) ((MPlaceholder) newValue)
					.getRef() : (MPart) element;
			CTabItem cti = findItemForPart(part);

			if (cti == null) {
				return;
			}

			if (CSSConstants.CSS_CONTENT_CHANGE_CLASS.equals(newValue)) {
				part.getTags().remove(CSSConstants.CSS_CONTENT_CHANGE_CLASS);
				if (cti != cti.getParent().getSelection()) {
					part.getTags().add(CSSConstants.CSS_HIGHLIGHTED_CLASS);
				}
			} else if (newValue instanceof MPlaceholder // part gets active
					&& part.getTags().contains(
							CSSConstants.CSS_HIGHLIGHTED_CLASS)) {
				part.getTags().remove(CSSConstants.CSS_HIGHLIGHTED_CLASS);
			}

			setCSSInfo(part, cti);
			reapplyStyles(cti);
		}

		public boolean validateElement(MUIElement element) {
			return element instanceof MPart || element instanceof MPartStack;
		}

		public boolean validateValues(Object oldValue, Object newValue) {
			return newValue instanceof MPlaceholder // part gets active
					|| isTagAdded(CSSConstants.CSS_BUSY_CLASS, oldValue,
							newValue) // part gets busy
					|| isTagRemoved(CSSConstants.CSS_BUSY_CLASS, oldValue,
							newValue) // part gets idle
					|| isTagAdded(CSSConstants.CSS_CONTENT_CHANGE_CLASS,
							oldValue, newValue); // content of part changed
		}

		private boolean isTagAdded(String tagName, Object oldValue,
				Object newValue) {
			return oldValue == null && tagName.equals(newValue);
		}

		private boolean isTagRemoved(String tagName, Object oldValue,
				Object newValue) {
			return newValue == null && tagName.equals(oldValue);
		}
	}
