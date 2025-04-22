        fields.add(editor);
    }

    /**
     * Adjust the layout of the field editors so that
     * they are properly aligned.
     */
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

    /**
     * Applys the font to the field editors managed by this page.
     */
    protected void applyFont() {
        if (fields != null) {
            Iterator<FieldEditor> e = fields.iterator();
            while (e.hasNext()) {
                FieldEditor pe = e.next();
                pe.applyFont();
            }
        }
    }

    /**
     * Calculates the number of columns needed to host all field editors.
     *
     * @return the number of columns
     */
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

    /**
     * Recomputes the page's error state by calling <code>isValid</code> for
     * every field editor.
     */
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
