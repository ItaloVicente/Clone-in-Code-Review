		fields.add(editor);
	}

	protected void adjustGridLayout() {
		int numColumns = calcNumberOfColumns();
		((GridLayout) fieldEditorParent.getLayout()).numColumns = numColumns;
		if (fields != null) {
			for (int i = 0; i < fields.size(); i++) {
				FieldEditor fieldEditor = fields.get(i);
				fieldEditor.adjustForNumColumns(numColumns);
			}
		}
	}

	protected void applyFont() {
		if (fields != null) {
			Iterator<FieldEditor> e = fields.iterator();
			while (e.hasNext()) {
				FieldEditor pe = e.next();
				pe.applyFont();
			}
		}
	}

	private int calcNumberOfColumns() {
		int result = 0;
		if (fields != null) {
			Iterator<FieldEditor> e = fields.iterator();
			while (e.hasNext()) {
				FieldEditor pe = e.next();
				result = Math.max(result, pe.getNumberOfControls());
			}
		}
		return result;
	}

	protected void checkState() {
		boolean valid = true;
		invalidFieldEditor = null;
		if (fields != null) {
			int size = fields.size();
			for (int i = 0; i < size; i++) {
				FieldEditor editor = fields.get(i);
				valid = valid && editor.isValid();
				if (!valid) {
					invalidFieldEditor = editor;
					break;
				}
			}
		}
		setValid(valid);
	}

	@Override
