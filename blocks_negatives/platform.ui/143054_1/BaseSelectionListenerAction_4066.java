public abstract class BaseSelectionListenerAction extends Action implements
        ISelectionChangedListener {
    /**
     * The current selection.
     */
    private IStructuredSelection selection = new StructuredSelection();
