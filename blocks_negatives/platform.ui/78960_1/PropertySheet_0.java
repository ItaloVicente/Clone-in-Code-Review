	/**
	 * Defines the dirty state indication behavior of the {@link PropertySheet}
	 * instance for the current tracked part if it is a {@link ISaveablePart}
	 * instance or provides an adapter to {@link ISaveablePart}.
	 * <p>
	 * Default return value is {@code false} - the Properties view will not show
	 * the '*' sign if the tracked part is dirty.
	 * <p>
	 * This behavior can be changed by either contributing custom
	 * {@link IPropertySheetPage} to the tracked part, or providing
	 * {@link ISecondarySaveableSource} adapter by the tracked part or by
	 * contributing {@link ISecondarySaveableSource} adapter to the
	 * {@link PropertySheet} class.
	 * <p>
	 * Strategy for the search is going from the smallest scope to the global
	 * scope, searching for the first {@link ISecondarySaveableSource} adapter.
	 * <p>
	 * The current page is asked for the {@link ISecondarySaveableSource}
	 * adapter first, if the adapter is not defined, the current tracked part is
	 * asked for it, and finally the platform adapter manager is consulted. The
	 * first adapter found in the steps above defines the return value of this
	 * method.
	 * <p>
	 * If the contributed page wants change the behavior The page must implement
	 * {@link IAdaptable} and return adapter to
	 * {@link ISecondarySaveableSource}.
	 *
	 * @return returns {@code false} by default.
	 * @since 3.9
	 */
	@Override
	public boolean isDirtyStateSupported() {
		if (currentPart == null) {
			return false;
		}
		ISecondarySaveableSource source = getAdapter(ISecondarySaveableSource.class);
		if (source != null && source != this) {
			return source.isDirtyStateSupported();
		}
		source = Adapters.adapt(currentPart, ISecondarySaveableSource.class);
		if (source != null && source != this) {
			return source.isDirtyStateSupported();
		}

		return false;
	}

	/**
