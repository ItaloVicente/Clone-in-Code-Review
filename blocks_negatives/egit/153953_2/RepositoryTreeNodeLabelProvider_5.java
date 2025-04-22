	 * Keeps the last label. If the label we originally get is undecorated, we
	 * return this last decorated label instead to prevent flickering. When the
	 * asynchronous lightweight decorator then has computed the decoration, the
	 * label will be updated. Note that this works only because our
	 * RepositoryTreeNodeDecorator always decorates! (If there's no decoration,
	 * it appends a single blank to ensure the decorated label is different from
	 * the undecorated one.)
	 * <p>
	 * For images, there is no such work-around, and thus we need to do the
	 * image decorations in the label provider (in the
	 * RepositoryTreeNodeWorkbenchAdapter in our case) in the UI thread.
	 */
	private final WeakHashMap<Object, StyledString> previousDecoratedLabels = new WeakHashMap<>();

	/**
	 * Creates a new {@link RepositoryTreeNodeLabelProvider} that shows the
	 * paths for repositories and working tree nodes.
