		Viewer<TestElement> viewer = getBrowser().getViewer();
		if (viewer instanceof StructuredViewer) {
			StructuredViewer<TestElement, TestElement> v = (StructuredViewer<TestElement, TestElement>) viewer;
			v.resetFilters();
		}
	}
