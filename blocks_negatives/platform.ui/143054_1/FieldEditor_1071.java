            label.addDisposeListener(event -> label = null);
        } else {
            checkParent(label, parent);
        }
        return label;
    }

    /**
     * Returns this field editor's label text.
     *
     * @return the label text
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * Returns the number of basic controls this field editor consists of.
     *
     * @return the number of controls
     */
    public abstract int getNumberOfControls();

    /**
     * Returns the name of the preference this field editor operates on.
     *
     * @return the name of the preference
     */
    public String getPreferenceName() {
        return preferenceName;
    }

    /**
     * Returns the preference page in which this field editor
     * appears.
     *
     * @return the preference page, or <code>null</code> if none
     * @deprecated use #getPage()
     */
    @Deprecated
