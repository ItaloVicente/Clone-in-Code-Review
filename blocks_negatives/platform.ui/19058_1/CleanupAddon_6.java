			Boolean toBeRendered = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (toBeRendered) {
				if (!container.isToBeRendered())
					container.setToBeRendered(true);
				if (!container.isVisible()
						&& !container.getTags().contains(IPresentationEngine.MINIMIZED))
					container.setVisible(true);
