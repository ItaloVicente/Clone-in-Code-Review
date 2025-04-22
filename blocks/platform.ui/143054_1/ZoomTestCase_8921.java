		return toTest.getTags().contains(IPresentationEngine.MAXIMIZED);
	}

	protected void assertZoomed(IWorkbenchPart part) {
		if (part == null) {
			Assert.assertFalse("Page should not be zoomed", isZoomed());
		} else {
			Assert.assertTrue("Expecting " + partName(part) + " to be zoomed", isZoomed(part));
			Assert.assertTrue("Page should be zoomed", isZoomed());
		}
	}

	protected void assertActive(IWorkbenchPart part) {
		IWorkbenchPart activePart = page.getActivePart();

		Assert.assertTrue("Unexpected active part: expected " + partName(part)
				+ " and found " + partName(activePart), activePart == part);

		if (part instanceof IEditorPart) {
			assertActiveEditor((IEditorPart)part);
		}
	}

	protected String partName(IWorkbenchPart part) {
		if (part == null) {
			return "null";
		}

		return Util.safeString(part.getTitle());
	}

	protected void assertActiveEditor(IEditorPart part) {
		IWorkbenchPart activeEditor = page.getActiveEditor();

		Assert.assertTrue("Unexpected active editor: expected " + partName(part)
				+ " and found " + partName(activeEditor), activeEditor == part);
	}

	protected boolean isZoomed() {
		return page.isPageZoomed();
	}

	public void close(IWorkbenchPart part) {
		if (part instanceof IViewPart) {
			page.hideView((IViewPart)part);
		} else if (part instanceof IEditorPart) {
			page.closeEditor((IEditorPart)part, false);
		}
	}
