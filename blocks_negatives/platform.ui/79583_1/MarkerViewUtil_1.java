
	/**
	 * Returns the id of the view used to show markers of the same type as the
	 * given marker using.legacy support
	 *
	 * @param marker
	 *            the marker
	 * @return the view id or <code>null</code> if no appropriate view could
	 *         be determined
	 * @throws CoreException
	 *             if an exception occurs testing the type of the marker
	 */
	private static String getLegacyViewId(IMarker marker) throws CoreException {
		String viewId = getViewId(marker);
		if (viewId == null)
			return null;
		return viewId + MarkerSupportInternalUtilities.LEGACY_SUFFIX;
	}

