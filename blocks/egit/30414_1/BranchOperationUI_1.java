				if (dialog.open() != Window.OK)
					return;

				dialogResult[0] = dialog.getRefName();
			}
		});
		return dialogResult[0];
