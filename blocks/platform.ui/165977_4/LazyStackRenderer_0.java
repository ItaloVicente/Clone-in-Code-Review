	@Inject
	@Optional
	private void subscribePartTopicToolbar(@UIEventTopic(UIEvents.Part.TOPIC_TOOLBAR) Event event) {
		Object obj = event.getProperty(UIEvents.EventTags.ELEMENT);
		Object value = event.getProperty(UIEvents.EventTags.NEW_VALUE);
		if (!(obj instanceof MPart) || !(value instanceof MToolBar)) {
			return;
		}

		MUIElement element = (MUIElement) obj;
		if (element.getCurSharedRef() != null) {
			element = element.getCurSharedRef();
		}

		MElementContainer<MUIElement> parent = element.getParent();
		if (parent.getRenderer() != LazyStackRenderer.this) {
			return;
		}

		MToolBar toolbar = (MToolBar) value;
		if (element == parent.getSelectedElement()) {
			toolbar.setVisible(true);
		} else {
			toolbar.setVisible(false);
		}
	}

