			checkBox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					boolean isSelected = checkBox.getSelection();
					valueChanged(wasSelected, isSelected);
					wasSelected = isSelected;
				}
			});
