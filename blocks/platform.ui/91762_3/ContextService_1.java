		try {
			deferUpdates(true);
			final Iterator<?> activationItr = activations.iterator();
			while (activationItr.hasNext()) {
				final IContextActivation activation = (IContextActivation) activationItr.next();
				deactivateContext(activation);
			}
		} finally {
			deferUpdates(false);
