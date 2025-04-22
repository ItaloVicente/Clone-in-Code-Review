		super.dispose();
		if (fields != null) {
			Iterator<FieldEditor> e = fields.iterator();
			while (e.hasNext()) {
				FieldEditor pe = e.next();
				pe.setPage(null);
				pe.setPropertyChangeListener(null);
				pe.setPreferenceStore(null);
			}
		}
	}

	protected Composite getFieldEditorParent() {
		if (style == FLAT) {
			Composite parent = new Composite(fieldEditorParent, SWT.NULL);
			parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			return parent;
		}
		return fieldEditorParent;
	}

	protected void initialize() {
		if (fields != null) {
			Iterator<FieldEditor> e = fields.iterator();
			while (e.hasNext()) {
				FieldEditor pe = e.next();
				pe.setPage(this);
				pe.setPropertyChangeListener(this);
				pe.setPreferenceStore(getPreferenceStore());
				pe.load();
			}
		}
	}

	@Override
