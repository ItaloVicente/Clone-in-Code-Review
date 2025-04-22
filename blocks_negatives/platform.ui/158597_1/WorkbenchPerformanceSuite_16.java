	private void addPerspectiveSwitchScenarios() {
		for (String[] PERSPECTIVE_SWITCH_PAIR : PERSPECTIVE_SWITCH_PAIRS) {
			addTest(new PerspectiveSwitchTest(PERSPECTIVE_SWITCH_PAIR, BasicPerformanceTest.NONE));
		}
	}

	public static String[] getAllPerspectiveIds() {
		ArrayList<String> result = new ArrayList<>();
		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();

		for (IPerspectiveDescriptor descriptor : perspectives) {
			String id = descriptor.getId();
			result.add(id);
		}

		return result.toArray(new String[result.size()]);
	}
