	public void testSimpleWindowLeak() throws Exception {
		manageWindows(false);
		try {
			ReferenceQueue queue = new ReferenceQueue();
			IWorkbenchWindow newWindow = openTestWindow();

			assertNotNull(newWindow);
			Reference ref = createReference(queue, newWindow);
			try {
				newWindow.close();
				newWindow = null;
				checkRef(queue, ref);
			} finally {
				ref.clear();
			}
		} finally {
			manageWindows(true);
		}
	}
