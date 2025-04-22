			if (defaultValue == null) {
				return MessageFormat.format(
						CoreText.ReportingTypedConfigGetter_invalidConfigWithLocationIgnored,
						location, entry);
			} else {
				return MessageFormat.format(
						CoreText.ReportingTypedConfigGetter_invalidConfigWithLocation,
						location, entry, defaultValue);
			}
