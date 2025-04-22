	private final class BranchNormalizer implements ModifyListener {
		private static final String UNDERSCORE = "_"; //$NON-NLS-1$

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
			String name = nameText.getText();
			if (!isPaste()) {
				name = name.replaceAll("\\s$", UNDERSCORE);//$NON-NLS-1$
			}
			name = Repository.normalizeBranchName(name);
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
