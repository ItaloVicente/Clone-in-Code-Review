				selectedRenamable
						.addValueChangeListener(new IValueChangeListener() {
							@Override
							public void handleValueChange(ValueChangeEvent event) {
								boolean shouldEnable = selectedRenamable
										.getValue() != null;
								removeButton.setEnabled(shouldEnable);
								renameButton.setEnabled(shouldEnable);
							}
						});
