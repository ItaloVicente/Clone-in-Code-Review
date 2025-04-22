
	private Rectangle getBounds(final Event event) {
		final Rectangle bounds;
		if (event.item instanceof TableItem)
			bounds = ((TableItem) event.item).getBounds(event.index);
		else if (event.item instanceof TreeItem)
			bounds = ((TreeItem) event.item).getBounds(event.index);
		else
			throw new IllegalArgumentException(
					"Event source item should be instanceof " //$NON-NLS-1$
						+ TableItem.class.getName() + " or " //$NON-NLS-1$
						+ TreeItem.class.getName() + ". Given element " //$NON-NLS-1$
						+ event.item.getClass().getName() + " is unsupported."); //$NON-NLS-1$
		return bounds;
	}
