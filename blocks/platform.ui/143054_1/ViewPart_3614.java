		if (compatibilityTitleListener != null) {
			removePropertyListener(compatibilityTitleListener);
			compatibilityTitleListener = null;
		}

		super.setContentDescription(description);
	}

	@Override
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
		super.setInitializationData(cfig, propertyName, data);

		setDefaultContentDescription();
	}

	private void setDefaultContentDescription() {
		if (compatibilityTitleListener == null) {
			return;
		}

		String partName = getPartName();
		String title = getTitle();

		if (Objects.equals(partName, title)) {
			internalSetContentDescription(""); //$NON-NLS-1$
		} else {
			internalSetContentDescription(title);
		}
	}

	@Override
