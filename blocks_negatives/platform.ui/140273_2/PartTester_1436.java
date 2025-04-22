		Assert.isNotNull(part.toString(),

        part.hashCode();

        if (part instanceof IWorkbenchPart2) {
            testWorkbenchPart2((IWorkbenchPart2)part);
        }
    }

    private static void testWorkbenchPart2(IWorkbenchPart2 part)
			throws Exception {
		Assert.isNotNull(part.getContentDescription(),
		Assert.isNotNull(part.getPartName(),
    }

    /**
     * Sanity-check the public interface of a view. This is called on every view after it
     * is fully initiallized, but before it is actually connected to the part reference or the
     * layout. Calls as much of the part's public interface as possible without modifying the part
     * to test for exceptions and check the return values for glaring faults. This does not need
     * to be an exhaustive conformance test, as it is called every time an editor is opened and
     * it needs to be efficient.
     *
     * @param part
     */
    public static void testView(IViewPart part) throws Exception {
       Assert.isTrue(part.getSite() == part.getViewSite(),
       testWorkbenchPart(part);
    }
