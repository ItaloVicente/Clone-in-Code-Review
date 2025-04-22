	private static boolean debug[] = new boolean[LAST_VALUE + 1];

	private static String[] events = new String[LAST_VALUE + 1];

	static {
		events[CREATE_PART] = PlatformUI.PLUGIN_ID + "/perf/part.create"; //$NON-NLS-1$
		events[CREATE_PART_INPUT] = PlatformUI.PLUGIN_ID + "/perf/part.input"; //$NON-NLS-1$
		events[CREATE_PART_CONTROL] = PlatformUI.PLUGIN_ID + "/perf/part.control"; //$NON-NLS-1$
		events[INIT_PART] = PlatformUI.PLUGIN_ID + "/perf/part.init"; //$NON-NLS-1$
		events[CREATE_PERSPECTIVE] = PlatformUI.PLUGIN_ID + "/perf/perspective.create"; //$NON-NLS-1$
		events[SWITCH_PERSPECTIVE] = PlatformUI.PLUGIN_ID + "/perf/perspective.switch"; //$NON-NLS-1$
		events[RESTORE_WORKBENCH] = PlatformUI.PLUGIN_ID + "/perf/workbench.restore"; //$NON-NLS-1$
		events[START_WORKBENCH] = PlatformUI.PLUGIN_ID + "/perf/workbench.start"; //$NON-NLS-1$
		events[ACTIVATE_PART] = PlatformUI.PLUGIN_ID + "/perf/part.activate"; //$NON-NLS-1$
		events[BRING_PART_TO_TOP] = PlatformUI.PLUGIN_ID + "/perf/part.activate"; //$NON-NLS-1$
		events[NOTIFY_PART_LISTENERS] = PlatformUI.PLUGIN_ID + "/perf/part.listeners"; //$NON-NLS-1$
		events[NOTIFY_PAGE_LISTENERS] = PlatformUI.PLUGIN_ID + "/perf/page.listeners"; //$NON-NLS-1$
		events[NOTIFY_PERSPECTIVE_LISTENERS] = PlatformUI.PLUGIN_ID + "/perf/perspective.listeners"; //$NON-NLS-1$
		events[UI_JOB] = PlatformUI.PLUGIN_ID + "/perf/uijob"; //$NON-NLS-1$
