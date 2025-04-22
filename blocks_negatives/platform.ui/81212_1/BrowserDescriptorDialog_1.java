			text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					listener.valueChanged(text.getText());
				}
			});
