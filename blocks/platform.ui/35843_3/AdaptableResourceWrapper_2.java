        if (adapter == IResource.class) {
			return resource;
		}
        if (adapter == IWorkbenchAdapter.class) {
			return TestAdaptableWorkbenchAdapter.getInstance();
		}
