				if (textChanged) {
					boolean rightStyle = (ti.getParent().getStyle() & SWT.RIGHT) != 0;
					if (rightStyle || !ti.getText().equals(textToSet)) {
						ti.setText(textToSet);
					}
				}
