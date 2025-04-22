    public static boolean DEBUG_SOURCES = DEFAULT;

    /**
     * Whether to print information about key bindings that are successfully
     * recognized within the system (as the keys are pressed).
     */
    public static boolean DEBUG_KEY_BINDINGS = DEFAULT;

    /**
     * Whether to print information about every key seen by the system.
     */
    public static boolean DEBUG_KEY_BINDINGS_VERBOSE = DEFAULT;

    /**
     * Whether to print extra information about error conditions dealing with
     * cool bars in the workbench, and their disposal.
     */
    public static boolean DEBUG_TOOLBAR_DISPOSAL = DEFAULT;

    /**
     * Whether to print debugging information about the execution of commands
     */
    public static boolean DEBUG_COMMANDS = DEFAULT;

    /**
     * Whether to print debugging information about the internal state of the
     * context support within the workbench.
     */
    public static boolean DEBUG_CONTEXTS = DEFAULT;

    /**
     * Whether to print debugging information about the performance of context
     * computations.
     */
    public static boolean DEBUG_CONTEXTS_PERFORMANCE = DEFAULT;

    /**
     * Whether to print even more debugging information about the internal state
     * of the context support within the workbench.
     */
    public static boolean DEBUG_CONTEXTS_VERBOSE = DEFAULT;

    /**
     * Whether to print debugging information about the internal state of the
     * command support (in relation to handlers) within the workbench.
     */
    public static boolean DEBUG_HANDLERS = DEFAULT;

    /**
     * Whether to print debugging information about the performance of handler
     * computations.
     */
    public static boolean DEBUG_HANDLERS_PERFORMANCE = DEFAULT;

    /**
     * Whether to print out verbose information about changing handlers in the
     * workbench.
     */
    public static boolean DEBUG_HANDLERS_VERBOSE = DEFAULT;

    /**
     * Whether to print debugging information about unexpected occurrences and
     * important state changes in the operation history.
     */
    public static boolean DEBUG_OPERATIONS = DEFAULT;

    /**
     * Whether to print out verbose information about the operation histories,
     * including all notifications sent.
     */
    public static boolean DEBUG_OPERATIONS_VERBOSE = DEFAULT;


    /**
     * Whether or not to show system jobs at all times.
     */
    public static boolean DEBUG_SHOW_ALL_JOBS = DEFAULT;

    /**
     * Whether or not to resolve images as they are declared.
     *
     * @since 3.1
     */
    public static boolean DEBUG_DECLARED_IMAGES = DEFAULT;

    /**
     * Whether or not to print contribution-related issues.
     *
     * @since 3.1
     */
    public static boolean DEBUG_CONTRIBUTIONS = DEFAULT;

    /**
     * Which command identifier to print handler information for.  This
     * restricts the debugging output, so a developer can focus on one command
     * at a time.
     */
    public static String DEBUG_HANDLERS_VERBOSE_COMMAND_ID = null;

    /**
     * Whether experimental features in the rendering of commands into menus
     * and toolbars should be enabled.  This is not guaranteed to provide a
     * working workbench.
     */
    public static boolean EXPERIMENTAL_MENU = DEFAULT;

    public static boolean DEBUG_MPE = DEFAULT;

    /**
     * Whether or not additional working set logging will occur.
     *
     * @since 3.4
     */
    public static boolean DEBUG_WORKING_SETS = DEFAULT;

    static {
            DEBUG_HANDLERS_VERBOSE_COMMAND_ID = Platform
                    .getDebugOption(PlatformUI.PLUGIN_ID
            	DEBUG_HANDLERS_VERBOSE_COMMAND_ID = null;
            }

            if(DEBUG_SWT_DEBUG_GLOBAL)
            	Device.DEBUG = true;
        }
    }

    private static boolean getDebugOption(String option) {
    }
