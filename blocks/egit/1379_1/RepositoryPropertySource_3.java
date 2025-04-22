
				@Override
				protected void setDirty(boolean dirty) {
					getButton(IDialogConstants.OK_ID).setEnabled(dirty);
				}
			};

			Control result = editor.createContents();
