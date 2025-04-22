		while (resources.hasNext()) {
			IProject currentResource = (IProject) resources.next();
			if (currentResource.isOpen()) {
				if (hasOpenProjects) {
					setText(pluralText);
					setToolTipText(pluralTooltip);
					break;
				}
				hasOpenProjects = true;
			}
		}
		return hasOpenProjects;
