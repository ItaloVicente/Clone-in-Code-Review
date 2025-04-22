			ControlDecoration deco = new ControlDecoration(button, SWT.TOP | SWT.RIGHT);
			Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_WARNING)
					.getImage();
			deco.setDescriptionText(IDEWorkbenchMessages.CleanDialog_copySettingsDecoLabel);
			deco.setImage(image);

			toggleDecoForSettingsImportButtons(button, deco);
			getCombo().addModifyListener(e -> toggleDecoForSettingsImportButtons(button, deco));

