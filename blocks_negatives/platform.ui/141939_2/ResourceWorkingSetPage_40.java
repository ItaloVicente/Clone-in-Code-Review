public class ResourceWorkingSetPage extends WizardPage implements
        IWorkingSetPage {
    private static final int SIZING_SELECTION_WIDGET_WIDTH = 50;

    private static final int SIZING_SELECTION_WIDGET_HEIGHT = 200;

    private Text text;

    private CheckboxTreeViewer tree;

    private IWorkingSet workingSet;


    /**
     * Creates a new instance of the receiver.
     */
    public ResourceWorkingSetPage() {
        super(
                "resourceWorkingSetPage", //$NON-NLS-1$
                IDEWorkbenchMessages.ResourceWorkingSetPage_title,
                IDEInternalWorkbenchImages
                        .getImageDescriptor(IDEInternalWorkbenchImages.IMG_WIZBAN_RESOURCEWORKINGSET_WIZ));
        setDescription(IDEWorkbenchMessages.ResourceWorkingSetPage_description);
    }

    /**
     * Adds working set elements contained in the given container to the list
     * of checked resources.
     *
     * @param collectedResources list of collected resources
     * @param container container to collect working set elements for
     */
    private void addWorkingSetElements(List collectedResources,
            IContainer container) {
        IPath containerPath = container.getFullPath();
