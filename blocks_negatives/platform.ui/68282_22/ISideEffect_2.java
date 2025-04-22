	 * Runs the given supplier until it returns a non-null result. The first
	 * time it returns a non-null result, that result will be passed to the
	 * consumer and the ISideEffect will dispose itself. As long as the supplier
	 * returns null, any tracked getters it invokes will be monitored for
	 * changes. If they change, the supplier will be run again at some point in
	 * the future.
	 * <p>
	 * The resulting ISideEffect will be dirty and resumed, so the first
	 * invocation of the supplier will be asynchronous. If the caller needs it
	 * to be invoked synchronously, they can call {@link #runIfDirty()}
	 * <p>
	 * Unlike {@link #create(Supplier, Consumer)}, the consumer does not need to
	 * be idempotent.
	 * <p>
	 * This method is used for gathering asynchronous data before opening an
	 * editor, saving to disk, opening a dialog box, or doing some other
	 * operation which should only be performed once.
	 * <p>
	 * Consider the following example, which displays the content of a text file
	 * in a message box without doing any file I/O on the UI thread.
	 * <p>
	 *
	 * <pre>
	 * IObservableValue&lt;String&gt; loadFileAsString(String filename) {
	 * }
	 *
	 * void showFileContents(Shell parentShell, String filename) {
	 *   IObservableValue&lt;String&gt; webPageContent = loadFileAsString(filename);
	 *   ISideEffect.runOnce(webPageContent::getValue, (content) -&gt; {
	 *   	MessageDialog.openInformation(parentShell, "Your file contains", content);
	 *   })
	 * }
	 * </pre>
