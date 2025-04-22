						.addValueChangeListener(new IValueChangeListener<RenamableItem>() {
							@Override
					public void handleValueChange(ValueChangeEvent<? extends RenamableItem> event) {
						boolean shouldEnable = selectedRenamable.getValue() != null;
						removeButton.setEnabled(shouldEnable);
						renameButton.setEnabled(shouldEnable);
					}
				});
