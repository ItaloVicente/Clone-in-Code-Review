	public void setRelativeDate(boolean relative) {
		dateFormatter = null;
		if (relative)
			format = Format.RELATIVE;
		else
			format = Format.LOCALELOCAL;
