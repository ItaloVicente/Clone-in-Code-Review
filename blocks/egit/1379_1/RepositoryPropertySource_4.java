		protected void okPressed() {
			try {
				editor.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			super.okPressed();
