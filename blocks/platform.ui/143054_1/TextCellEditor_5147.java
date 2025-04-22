				TextCellEditor.this.focusLost();
			}
		});
		text.setFont(parent.getFont());
		text.setBackground(parent.getBackground());
		text.setText("");//$NON-NLS-1$
		text.addModifyListener(getModifyListener());
		return text;
	}

	@Override
