		if (icon == null) {
			ImageDescriptor desc = contributor.getPageIcon();
			if (desc != null) {
				icon = desc.createImage();
			}
		}
		return icon;
	}
