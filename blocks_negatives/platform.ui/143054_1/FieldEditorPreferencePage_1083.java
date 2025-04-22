        if (fields != null) {
            Iterator<FieldEditor> e = fields.iterator();
            while (e.hasNext()) {
                FieldEditor pe = e.next();
                pe.loadDefault();
            }
        }
        checkState();
        super.performDefaults();
    }

    /**
     * The field editor preference page implementation of this
     * <code>PreferencePage</code> method saves all field editors by
     * calling <code>FieldEditor.store</code>. Note that this method
     * does not save the preference store itself; it just stores the
     * values back into the preference store.
     *
     * @see FieldEditor#store()
     */
    @Override
