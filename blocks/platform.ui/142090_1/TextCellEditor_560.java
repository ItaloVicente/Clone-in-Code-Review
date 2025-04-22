			int pos = text.getCaretPosition();
			if (pos < text.getCharCount()) {
				text.setSelection(pos, pos + 1);
				text.insert(""); //$NON-NLS-1$
			}
		}
		checkSelection();
		checkDeleteable();
		checkSelectable();
	}

	@Override
