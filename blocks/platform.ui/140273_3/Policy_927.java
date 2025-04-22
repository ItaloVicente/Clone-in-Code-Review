	public static boolean DEBUG_SOURCES = DEFAULT;

	public static boolean DEBUG_KEY_BINDINGS = DEFAULT;

	public static boolean DEBUG_KEY_BINDINGS_VERBOSE = DEFAULT;

	public static boolean DEBUG_TOOLBAR_DISPOSAL = DEFAULT;

	public static boolean DEBUG_COMMANDS = DEFAULT;

	public static boolean DEBUG_CONTEXTS = DEFAULT;

	public static boolean DEBUG_CONTEXTS_PERFORMANCE = DEFAULT;

	public static boolean DEBUG_CONTEXTS_VERBOSE = DEFAULT;

	public static boolean DEBUG_HANDLERS = DEFAULT;

	public static boolean DEBUG_HANDLERS_PERFORMANCE = DEFAULT;

	public static boolean DEBUG_HANDLERS_VERBOSE = DEFAULT;

	public static boolean DEBUG_OPERATIONS = DEFAULT;

	public static boolean DEBUG_OPERATIONS_VERBOSE = DEFAULT;

	public static boolean DEBUG_SHOW_ALL_JOBS = DEFAULT;

	public static boolean DEBUG_DECLARED_IMAGES = DEFAULT;

	public static boolean DEBUG_CONTRIBUTIONS = DEFAULT;

	public static String DEBUG_HANDLERS_VERBOSE_COMMAND_ID = null;

	public static boolean EXPERIMENTAL_MENU = DEFAULT;

	public static boolean DEBUG_MPE = DEFAULT;

	public static boolean DEBUG_WORKING_SETS = DEFAULT;

	static {
		if (getDebugOption("/debug")) { //$NON-NLS-1$
			DEBUG_SWT_GRAPHICS = getDebugOption("/trace/graphics"); //$NON-NLS-1$
			DEBUG_SWT_DEBUG = getDebugOption("/debug/swtdebug"); //$NON-NLS-1$
			DEBUG_SWT_DEBUG_GLOBAL = getDebugOption("/debug/swtdebugglobal"); //$NON-NLS-1$
			DEBUG_DRAG_DROP = getDebugOption("/trace/dragDrop"); //$NON-NLS-1$
			DEBUG_SOURCES = getDebugOption("/trace/sources"); //$NON-NLS-1$
			DEBUG_KEY_BINDINGS = getDebugOption("/trace/keyBindings"); //$NON-NLS-1$
			DEBUG_KEY_BINDINGS_VERBOSE = getDebugOption("/trace/keyBindings.verbose"); //$NON-NLS-1$
			DEBUG_TOOLBAR_DISPOSAL = "true" //$NON-NLS-1$
					.equalsIgnoreCase(Platform.getDebugOption("org.eclipse.jface/trace/toolbarDisposal")); //$NON-NLS-1$
			DEBUG_COMMANDS = getDebugOption("/trace/commands"); //$NON-NLS-1$
			DEBUG_CONTEXTS = getDebugOption("/trace/contexts"); //$NON-NLS-1$
			DEBUG_CONTEXTS_PERFORMANCE = getDebugOption("/trace/contexts.performance"); //$NON-NLS-1$
			DEBUG_CONTEXTS_VERBOSE = getDebugOption("/trace/contexts.verbose"); //$NON-NLS-1$
			DEBUG_HANDLERS = getDebugOption("/trace/handlers"); //$NON-NLS-1$
			DEBUG_HANDLERS_PERFORMANCE = getDebugOption("/trace/handlers.performance"); //$NON-NLS-1$
			DEBUG_HANDLERS_VERBOSE = getDebugOption("/trace/handlers.verbose"); //$NON-NLS-1$
			DEBUG_OPERATIONS = getDebugOption("/trace/operations"); //$NON-NLS-1$
			DEBUG_OPERATIONS_VERBOSE = getDebugOption("/trace/operations.verbose"); //$NON-NLS-1$
			DEBUG_SHOW_ALL_JOBS = getDebugOption("/debug/showAllJobs"); //$NON-NLS-1$
			DEBUG_STALE_JOBS = getDebugOption("/debug/job.stale"); //$NON-NLS-1$
			DEBUG_HANDLERS_VERBOSE_COMMAND_ID = Platform
					.getDebugOption(PlatformUI.PLUGIN_ID + "/trace/handlers.verbose.commandId"); //$NON-NLS-1$
			if ("".equals(DEBUG_HANDLERS_VERBOSE_COMMAND_ID)) { //$NON-NLS-1$
				DEBUG_HANDLERS_VERBOSE_COMMAND_ID = null;
			}
			DEBUG_DECLARED_IMAGES = getDebugOption("/debug/declaredImages"); //$NON-NLS-1$
			DEBUG_CONTRIBUTIONS = getDebugOption("/debug/contributions"); //$NON-NLS-1$
			EXPERIMENTAL_MENU = getDebugOption("/experimental/menus"); //$NON-NLS-1$
			DEBUG_MPE = getDebugOption("/trace/multipageeditor"); //$NON-NLS-1$
			DEBUG_WORKING_SETS = getDebugOption("/debug/workingSets"); //$NON-NLS-1$

			if (DEBUG_SWT_DEBUG_GLOBAL)
				Device.DEBUG = true;
		}
	}

	private static boolean getDebugOption(String option) {
		return "true".equalsIgnoreCase(Platform.getDebugOption(PlatformUI.PLUGIN_ID + option)); //$NON-NLS-1$
	}
