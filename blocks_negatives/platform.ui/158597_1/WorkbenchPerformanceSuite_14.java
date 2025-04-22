	public static final String [][] PERSPECTIVE_SWITCH_PAIRS = {
		{"org.eclipse.jdt.ui.JavaPerspective", "org.eclipse.debug.ui.DebugPerspective", "1.java"},

		{UIPerformanceTestSetup.PERSPECTIVE1, UIPerformanceTestSetup.PERSPECTIVE2, "1.perf_basic"},

		{"org.eclipse.ui.tests.dnd.dragdrop", "org.eclipse.ui.tests.fastview_perspective", "1.perf_basic"},

		{"org.eclipse.jdt.ui.JavaPerspective", "org.eclipse.ui.tests.util.EmptyPerspective", "1.perf_basic"},

		{RESOURCE_PERSPID, "org.eclipse.jdt.ui.JavaPerspective", "1.java"}
	};

	public static final String[] VIEW_IDS = {
		"org.eclipse.ui.views.ProblemView",
		"org.eclipse.ui.views.ResourceNavigator"
	};
	public static final int ITERATIONS = 25;

