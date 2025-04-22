		while (resources.hasNext()) {
			IProject currentResource = (IProject) resources.next();
			if (currentResource.isOpen()) {
				if (hasOpenProjects) {
					setText(TEXT_PLURAL_STRING);
					setToolTipText(TOOLTIP_PLURAL_STRING);
					break;
				}
				hasOpenProjects = true;
			}
		}
		return hasOpenProjects;
