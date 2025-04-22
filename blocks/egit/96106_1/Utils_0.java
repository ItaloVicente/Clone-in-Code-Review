		if (adapter == null) {
			return null;
		}
		if (adapterClass.isInstance(adapter)) {
			return adapterClass.cast(adapter);
		} else {
			Activator.logError(
					MessageFormat.format(CoreText.Utils_InvalidAdapterError,
							adaptable.getClass().getName(),
							adapterClass.getName(),
							adapter.getClass().getName()),
					new IllegalStateException());
			return null;
		}
