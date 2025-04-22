	@Override
	public boolean isDirtyStateIndicationSupported() {
		if (currentPart == null) {
			return false;
		}
		ISecondarySaveableSource source = getAdapter(ISecondarySaveableSource.class);
		if (source != null && source != this) {
			return source.isDirtyStateIndicationSupported();
		}
		source = Adapters.adapt(currentPart, ISecondarySaveableSource.class);
		if (source != null && source != this) {
			return source.isDirtyStateIndicationSupported();
		}

		return ISecondarySaveableSource.super.isDirtyStateIndicationSupported();
	}

