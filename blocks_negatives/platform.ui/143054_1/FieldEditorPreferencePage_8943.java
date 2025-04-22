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

    /**
     * Returns a parent composite for a field editor.
     * <p>
     * This value must not be cached since a new parent
     * may be created each time this method called. Thus
     * this method must be called each time a field editor
     * is constructed.
     * </p>
     *
     * @return a parent
     */
    protected Composite getFieldEditorParent() {
        if (style == FLAT) {
            Composite parent = new Composite(fieldEditorParent, SWT.NULL);
            parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            return parent;
        }
        return fieldEditorParent;
    }

    /**
     * Initializes all field editors.
     */
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

    /**
     * The field editor preference page implementation of a <code>PreferencePage</code>
     * method loads all the field editors with their default values.
     */
    @Override
