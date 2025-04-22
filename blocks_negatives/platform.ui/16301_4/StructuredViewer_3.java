	 * NON-API - to be removed - see bug 200214
	 * Enable or disable the preserve selection behavior of this viewer. The
	 * default is that the viewer attempts to preserve the selection across
	 * update operations. This is an advanced option, to support clients that
	 * manage the selection without relying on the viewer, or clients running
	 * into performance problems when using viewers and {@link SWT#VIRTUAL}.
	 * Note that this method has been introduced in 3.5 and that trying to
	 * disable the selection behavior may not be possible for all subclasses of
	 * <code>StructuredViewer</code>, or may cause program errors. This method
	 * is supported for {@link TableViewer}, {@link TreeViewer},
	 * {@link ListViewer}, {@link CheckboxTableViewer},
	 * {@link CheckboxTreeViewer}, and {@link ComboViewer}, but no promises are
	 * made for other subclasses of StructuredViewer, or subclasses of the
	 * listed viewer classes.
