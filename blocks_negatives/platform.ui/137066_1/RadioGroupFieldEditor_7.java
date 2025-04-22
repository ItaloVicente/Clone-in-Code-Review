     * Creates a radio group field editor.
     * <p>
     * Example usage:
     * <pre>
     *		RadioGroupFieldEditor editor= new RadioGroupFieldEditor(
     *			"GeneralPage.DoubleClick", resName, 1,
     *			new String[][] {
     *				{"Open Browser", "open"},
     *				{"Expand Tree", "expand"}
     *			},
     *          parent,
     *          true);
     * </pre>
     * </p>
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param numColumns the number of columns for the radio button presentation
     * @param labelAndValues list of radio button [label, value] entries;
     *  the value is returned when the radio button is selected
     * @param parent the parent of the field editor's control
     * @param useGroup whether to use a Group control to contain the radio buttons
     */
