			name.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					final String text = name.getText();
					viewModel.getPerson().setName(text);
				}
