	/**
	 * Get the name of the element. If the marker has the
	 * MarkerViewUtil#NAME_ATTRIBUTE set use that. Otherwise use the name of the
	 * resource.
	 *
	 * @param marker
	 * @return String
	 */
	public static String getResourceName(IMarker marker) {

		if (!marker.exists()) {
			return Util.EMPTY_STRING;
		}

		try {
			Object nameAttribute = marker
					.getAttribute(MarkerViewUtil.NAME_ATTRIBUTE);

			if (nameAttribute != null) {
				return nameAttribute.toString();
			}
		} catch (CoreException exception) {
			Policy.handle(exception);
		}

		return marker.getResource().getName();
	}

