		@Override
		public void create() {
			super.create();
			setTitle(myTitle);
			setMessage(UIText.RepositoryPropertySource_EditorMessage);
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			createButton(parent, IDialogConstants.OK_ID,
					IDialogConstants.OK_LABEL, true);
		}
	}
