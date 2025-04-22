		/**
		 * Allow the user to finish if a valid file has been entered.
		 *
		 * @return page is valid
		 */
		protected boolean validatePage() {
			if (fsRadio.getSelection()) {
				pageValid = validateFilesystemLocation();
			} else if (cpRadio.getSelection()) {
				pageValid = true;
			}
