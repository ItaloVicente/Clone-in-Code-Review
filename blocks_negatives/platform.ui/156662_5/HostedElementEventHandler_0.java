		if (uiSync != null) {
			uiSync.syncExec(() -> {
				final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
				if (!changedElement.getTags().contains(ModelServiceImpl.HOSTED_ELEMENT)) {
					return;
				}

				if (changedElement.getWidget() != null) {
					return;
				}
