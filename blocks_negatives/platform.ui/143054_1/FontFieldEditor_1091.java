            font = new Font(text.getDisplay(), fontData);
            text.setFont(font);
        }

        /**
         * @return the preferred size of the previewer.
         */
        public int getPreferredExtent() {
            return 40;
        }
    }

    /**
     * Creates a new font field editor
     */
    protected FontFieldEditor() {
    }

    /**
     * Creates a font field editor with an optional preview area.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param previewAreaText the text used for the preview window. If it is
     * <code>null</code> there will be no preview area,
     * @param parent the parent of the field editor's control
     */
    public FontFieldEditor(String name, String labelText,
            String previewAreaText, Composite parent) {
        init(name, labelText);
        previewText = previewAreaText;
        createControl(parent);

    }

    /**
     * Creates a font field editor without a preview.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public FontFieldEditor(String name, String labelText, Composite parent) {
        this(name, labelText, null, parent);

    }

    @Override
