			Assert.isNotNull(persistableElement.getFactoryId(),
					"The persistable element for the editor input must have a non-null factory id"); //$NON-NLS-1$
		}
	}

	private static void testWorkbenchPart(IWorkbenchPart part) throws Exception {
		IPropertyListener testListener = (source, propId) -> {
