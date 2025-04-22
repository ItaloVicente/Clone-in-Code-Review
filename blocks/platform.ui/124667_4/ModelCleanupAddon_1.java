	private void iteratorRemove(List<?> list, List<?> elementsToBeRemoved) {
		if (elementsToBeRemoved.isEmpty()) {
			return;
		}

		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (elementsToBeRemoved.contains(object)) {
				iterator.remove();
			}

		}
	}

	private void logMissingClassWarning(MApplicationElement appElement) {
		StringBuilder sb = new StringBuilder();
		sb.append("Removing "); //$NON-NLS-1$
		sb.append(appElement.getClass().getSimpleName());
		sb.append(" with the \""); //$NON-NLS-1$
		sb.append(appElement.getElementId());
		sb.append("\" id"); //$NON-NLS-1$
		if (appElement instanceof MUILabel) {
			sb.append(" and the \""); //$NON-NLS-1$
			sb.append(((MUILabel) appElement).getLocalizedLabel());
			sb.append("\" label"); //$NON-NLS-1$
		}
		sb.append("."); //$NON-NLS-1$
		sb.append("It points to the non available \""); //$NON-NLS-1$
		sb.append(getContributionUri(appElement));
		sb.append("\" class. Bundle might have been uninstalled"); //$NON-NLS-1$

		logger.warn(sb.toString());
	}

	private boolean isValidAppElement(MApplicationElement uiElement) {
		String contributionURI = getContributionUri(uiElement);
