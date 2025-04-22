		String dateFormat = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.DATE_FORMAT);
		DateFormat date = null;
		if (dateFormat != null) {
			try {
				date = new SimpleDateFormat(dateFormat);
			} catch (IllegalArgumentException e) {
			}
		}
		if (date == null) {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$
		}
		this.fmt = date;
