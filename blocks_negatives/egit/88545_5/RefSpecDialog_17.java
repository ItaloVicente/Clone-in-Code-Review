		specString.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!specString.isFocusControl()
						|| getSpec().toString().equals(specString.getText()))
					return;
