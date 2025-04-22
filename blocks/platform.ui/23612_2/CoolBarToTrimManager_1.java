
		if (idx == -1) {
			List<MTrimElement> trimBarElements = trimBar.getChildren();
			idx = trimBarElements.size() - 1;
			while (idx > -1) {
				if ("PerspectiveSpacer".equals(trimBarElements.get(idx).getElementId())) { //$NON-NLS-1$
					break;
				}
				idx--;
			}
		}

