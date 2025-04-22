public class CheckboxTreeViewer extends TreeViewer implements ICheckable {

    /**
     * List of check state listeners (element type: <code>ICheckStateListener</code>).
     */
    private ListenerList checkStateListeners = new ListenerList();

    /**
     * Provides the desired state of the check boxes.
     */
    private ICheckStateProvider checkStateProvider;

    /**
     * Last item clicked on, or <code>null</code> if none.
     */
    private TreeItem lastClickedItem = null;

    /**
     * Creates a tree viewer on a newly-created tree control under the given parent.
     * The tree control is created using the SWT style bits: <code>CHECK</code> and <code>BORDER</code>.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param parent the parent control
     */
    public CheckboxTreeViewer(Composite parent) {
        this(parent, SWT.BORDER);
    }

    /**
     * Creates a tree viewer on a newly-created tree control under the given parent.
     * The tree control is created using the given SWT style bits, plus the <code>CHECK</code> style bit.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param parent the parent control
     * @param style the SWT style bits
     */
    public CheckboxTreeViewer(Composite parent, int style) {
        this(new Tree(parent, SWT.CHECK | style));
    }

    /**
     * Creates a tree viewer on the given tree control.
     * The <code>SWT.CHECK</code> style bit must be set on the given tree control.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param tree the tree control
     */
    public CheckboxTreeViewer(Tree tree) {
        super(tree);
    }

    @Override
