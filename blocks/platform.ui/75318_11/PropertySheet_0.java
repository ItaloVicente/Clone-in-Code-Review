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

