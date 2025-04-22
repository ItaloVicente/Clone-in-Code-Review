			Assert
					.isNotNull(persistableElement.getFactoryId(),
        }
    }

    /**
     * Sanity-checks a workbench part. Excercises the public interface and tests for any
     * obviously bogus return values. The part should be unmodified when the method exits.
     *
     * @param part
     * @throws Exception
     */
    private static void testWorkbenchPart(IWorkbenchPart part) throws Exception {
        IPropertyListener testListener = (source, propId) -> {
