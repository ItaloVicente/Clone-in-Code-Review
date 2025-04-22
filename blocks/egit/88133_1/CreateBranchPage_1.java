	private final class BranchNormalizer implements ModifyListener {
		private String oldName = ""; //$NON-NLS-1$

		private boolean listenerActive;

		@Override
		public void modifyText(ModifyEvent e) {
			nameText.setFocus();
			if (listenerActive || normalizeName.getSelection() == false)
				return;
			try {
				listenerActive = true;
				normalize();
			} finally {
				listenerActive = false;
			}
		}

		private void normalize() {
			String name = Repository.normalizeBranchName(nameText.getText(),
					isPaste());
			nameText.setText(name);
			nameText.setSelection(nameText.getText().length() + 1);
		}

		private boolean isPaste() {
			boolean result = Math
					.abs(oldName.length() - nameText.getText().length()) > 1;
			oldName = nameText.getText();
			return result;
		}
	}
