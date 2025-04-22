		Assert.isNotNull(part.toString(), "A part's toString method must return a non-null value"); //$NON-NLS-1$

		part.hashCode();

		if (part instanceof IWorkbenchPart2) {
			testWorkbenchPart2((IWorkbenchPart2) part);
		}
	}

	private static void testWorkbenchPart2(IWorkbenchPart2 part) throws Exception {
		Assert.isNotNull(part.getContentDescription(), "A part must return a non-null content description"); //$NON-NLS-1$
		Assert.isNotNull(part.getPartName(), "A part must return a non-null part name"); //$NON-NLS-1$
	}

	public static void testView(IViewPart part) throws Exception {
		Assert.isTrue(part.getSite() == part.getViewSite(), "A part's site must be the same as a part's view site"); //$NON-NLS-1$
		testWorkbenchPart(part);
	}
