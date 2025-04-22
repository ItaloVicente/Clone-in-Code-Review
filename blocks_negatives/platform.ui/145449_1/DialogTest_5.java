	 * {@link org.eclipse.jface.dialogs.Dialog#initializeBounds()
	 * initializeBounds()} invocation, the button will not be visually shifted
	 * even though getChildren() may prove otherwise. We check for this by
	 * comparing the X coordinate of the 'OK' and 'Cancel' buttons to ensure
	 * that they are in the right place if the dismissal alignment for the
	 * current platform is SWT.RIGHT.
