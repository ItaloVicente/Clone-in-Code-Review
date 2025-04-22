	/**
	 * Return the action bar presentation used for creating toolbars. This is
	 * for internal use only, used for consistency with the window.
	 * 
	 * @return the presentation used.
	 */
	public IActionBarPresentationFactory getActionBarPresentationFactory() {
		E4Util.unsupported("getActionBarPresentationFactory: doesn't do anything useful, should cause NPE"); //$NON-NLS-1$
		IActionBarPresentationFactory actionBarPresentation = null;
		AbstractPresentationFactory presentationFactory = getWindowConfigurer()
				.getPresentationFactory();
		if (presentationFactory instanceof IActionBarPresentationFactory) {
			actionBarPresentation = ((IActionBarPresentationFactory) presentationFactory);
		}

		return actionBarPresentation;
	}
