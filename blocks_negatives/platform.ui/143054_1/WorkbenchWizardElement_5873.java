        return selectionEnabler;
    }

    /**
     * Attempt to convert the elements in the passed selection into resources
     * by asking each for its IResource property (iff it isn't already a
     * resource). If all elements in the initial selection can be converted to
     * resources then answer a new selection containing these resources;
     * otherwise answer an empty selection.
     *
     * @param originalSelection the original selection
     * @return the converted selection or an empty selection
     */
	private IStructuredSelection convertToResources(
			IStructuredSelection originalSelection) {
		Object selectionService = PlatformUI.getWorkbench().getService(
				ISelectionConversionService.class);
