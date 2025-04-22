			if (defaultValue == null) {
				return MessageFormat.format(
						CoreText.ReportingTypedConfigGetter_invalidConfigIgnored,
						entry);
			} else {
				return MessageFormat.format(
						CoreText.ReportingTypedConfigGetter_invalidConfig,
						entry, defaultValue);
			}
