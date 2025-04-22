			if (enabled) {
				if (checked)
					return UIIcons.CHECKBOX_ENABLED_CHECKED.createImage();
				return UIIcons.CHECKBOX_ENABLED_UNCHECKED.createImage();
			}
			if (checked)
				return UIIcons.CHECKBOX_DISABLED_CHECKED.createImage();
			return UIIcons.CHECKBOX_DISABLED_UNCHECKED.createImage();
		}
