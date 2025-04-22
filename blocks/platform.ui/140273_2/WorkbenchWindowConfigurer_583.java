public final class WorkbenchWindowConfigurer implements IWorkbenchWindowConfigurer {

	private WorkbenchWindow window;

	private int shellStyle = SWT.SHELL_TRIM | Window.getDefaultOrientation();

	private String windowTitle;

	private boolean showPerspectiveBar = false;

	private boolean showStatusLine = true;

	private boolean showToolBar = true;

	private boolean showMenuBar = true;

	private boolean showProgressIndicator = false;

	private Map extraData = new HashMap(1);

	private ArrayList transferTypes = new ArrayList(3);

	private DropTargetListener dropTargetListener = null;

	private WindowActionBarConfigurer actionBarConfigurer = null;

	private Point initialSize = new Point(1024, 768);

	class WindowActionBarConfigurer implements IActionBarConfigurer2 {

		private IActionBarConfigurer2 proxy;

		public void setProxy(IActionBarConfigurer2 proxy) {
			this.proxy = proxy;
		}

		@Override
