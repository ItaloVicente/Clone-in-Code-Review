	private EventHandler widgetSetHandler = event -> {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
				&& event.getProperty(UIEvents.EventTags.NEW_VALUE) == null) {
			 Assert.isTrue(!composite.isDisposed(),
									"The widget should not have been disposed at this point"); //$NON-NLS-1$
			beingDisposed = true;
			WorkbenchPartReference reference = getReference();
			((WorkbenchPage) reference.getPage()).firePartDeactivatedIfActive(part);
			((WorkbenchPage) reference.getPage()).firePartHidden(part);
			((WorkbenchPage) reference.getPage()).firePartClosed(CompatibilityPart.this);
