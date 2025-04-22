	public static class Format implements Localizable {
		final String text;

		Format(String text) {
			this.text = text;
		}

		@Override
		public String formatWithLocale(Locale locale
			return format(args);
		}

		@Override
		public String format(Object... args) {
			return MessageFormat.format(text
		}
	}

	public static Format format(String text) {
		return new Format(text);
	}
