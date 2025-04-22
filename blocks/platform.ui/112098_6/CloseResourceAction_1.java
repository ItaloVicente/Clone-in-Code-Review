		while (resources.hasNext()) {
			IProject currentResource = (IProject) resources.next();
			if (currentResource.isOpen()) {
				if (hasOpenProjects) {
					setText(textPluralString);
					setToolTipText(tooltipPluralString);
					break;
				}
				hasOpenProjects = true;
			}
		}
		return hasOpenProjects;
