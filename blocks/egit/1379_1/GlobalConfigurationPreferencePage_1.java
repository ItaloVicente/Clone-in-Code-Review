
			@Override
			protected void setDirty(boolean dirty) {
				isDirty = dirty;
				updateApplyButton();
			}
		};
		Control result = editor.createContents();
