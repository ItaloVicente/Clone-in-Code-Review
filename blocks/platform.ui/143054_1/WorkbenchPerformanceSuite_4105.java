		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();

		for (IPerspectiveDescriptor descriptor : perspectives) {
			String id = descriptor.getId();
			result.add(id);
		}

		return result.toArray(new String[result.size()]);
	}

	private void addResizeScenarios() {
		String[] perspectiveIds = getAllPerspectiveIds();
		for (String id : perspectiveIds) {
			addTest(new ResizeTest(new PerspectiveWidgetFactory(id),
					id.equals(resizeFingerprintTest) ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE,
					"UI - Workbench Window Resize"));
		}
	}
