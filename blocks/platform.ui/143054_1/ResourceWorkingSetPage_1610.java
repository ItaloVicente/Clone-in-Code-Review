public class ResourceWorkingSetPage extends WizardPage implements IWorkingSetPage {
	private static final int SIZING_SELECTION_WIDGET_WIDTH = 50;

	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 200;

	private Text text;

	private CheckboxTreeViewer tree;

	private IWorkingSet workingSet;

	private boolean firstCheck = false; // set to true if selection is set in setSelection

	public ResourceWorkingSetPage() {
		super("resourceWorkingSetPage", //$NON-NLS-1$
				IDEWorkbenchMessages.ResourceWorkingSetPage_title, IDEInternalWorkbenchImages
						.getImageDescriptor(IDEInternalWorkbenchImages.IMG_WIZBAN_RESOURCEWORKINGSET_WIZ));
		setDescription(IDEWorkbenchMessages.ResourceWorkingSetPage_description);
	}

	private void addWorkingSetElements(List collectedResources, IContainer container) {
		IPath containerPath = container.getFullPath();
