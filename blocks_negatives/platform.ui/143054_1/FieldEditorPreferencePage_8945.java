        if (fields != null) {
            Iterator<FieldEditor> e = fields.iterator();
            while (e.hasNext()) {
                FieldEditor pe = e.next();
                pe.store();
                pe.setPresentsDefaultValue(false);
            }
        }
        return true;
    }

    /**
     * The field editor preference page implementation of this <code>IPreferencePage</code>
     * (and <code>IPropertyChangeListener</code>) method intercepts <code>IS_VALID</code>
     * events but passes other events on to its superclass.
     */
    @Override
