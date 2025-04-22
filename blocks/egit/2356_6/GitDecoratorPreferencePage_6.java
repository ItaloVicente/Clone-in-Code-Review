		private void updateDateFormatPreview() {
			SimpleDateFormat sdf;
			try {
				sdf = new SimpleDateFormat(dateFormat.getText());
				dateFormatPreview.setText(sdf.format(exapmleDate));
				formatValid = true;
			} catch (Exception ex) {
				dateFormatPreview.setText(UIText.DecoratorPreferencesPage_wrongDateFormat);
				formatValid = false;
