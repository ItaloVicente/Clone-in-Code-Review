	private String getIconURI(MUILabel element) {
		if (element instanceof MPart) {
			MPart part = (MPart) element;
			if (part.getTransientData().containsKey(ICON_URI_FROM_DESC)) {
				return (String) part.getTransientData().get(ICON_URI_FROM_DESC);
			}

			MPartDescriptor desc = modelService.getPartDescriptor(part
					.getElementId());
			if (desc != null && desc.getIconURI() != null) {
				part.getTransientData().put(ICON_URI_FROM_DESC,
						desc.getIconURI());
				return desc.getIconURI();
			}

		}
		return element.getIconURI();
	}

