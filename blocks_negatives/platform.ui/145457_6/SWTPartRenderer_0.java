			String iconURI = (String) part.getTransientData().get(
					ICON_URI_FOR_PART);
			if (iconURI != null) {
				return iconURI;
			}

			MPartDescriptor desc = modelService.getPartDescriptor(part
					.getElementId());
			iconURI = desc != null && desc.getIconURI() != null ? desc
					.getIconURI() : element.getIconURI();
			part.getTransientData().put(ICON_URI_FOR_PART, iconURI);

			return iconURI;
