     * Create a message dialog. Note that the dialog will have no visual
     * representation (no widgets) until it is told to open.
     * <p>
     * The labels of the buttons to appear in the button bar are supplied in
     * this constructor as an array. The <code>open</code> method will return
     * the index of the label in this array corresponding to the button that was
     * pressed to close the dialog.
     * </p>
     * <p>
     * <strong>Note:</strong> If the dialog was dismissed without pressing
     * a button (ESC key, close box, etc.) then {@link SWT#DEFAULT} is returned.
     * Note that the <code>open</code> method blocks.
     * </p>
     *
     * @param parentShell
     *            the parent shell, or <code>null</code> to create a top-level shell
     * @param dialogTitle
     *            the dialog title, or <code>null</code> if none
     * @param dialogTitleImage
     *            the dialog title image, or <code>null</code> if none
     * @param dialogMessage
     *            the dialog message
     * @param dialogImageType
     *            one of the following values:
     *            <ul>
     *            <li><code>MessageDialog.NONE</code> for a dialog with no
     *            image</li>
     *            <li><code>MessageDialog.ERROR</code> for a dialog with an
     *            error image</li>
     *            <li><code>MessageDialog.INFORMATION</code> for a dialog
     *            with an information image</li>
     *            <li><code>MessageDialog.QUESTION </code> for a dialog with a
     *            question image</li>
     *            <li><code>MessageDialog.WARNING</code> for a dialog with a
     *            warning image</li>
     *            </ul>
     * @param dialogButtonLabels
     *            an array of labels for the buttons in the button bar
     * @param defaultIndex
     *            the index in the button label array of the default button
     */
