		this.parentId = parentId;

		fireSchemeChanged(new SchemeEvent(this, definedChanged, nameChanged,
				descriptionChanged, parentIdChanged));
	}

	private final void fireSchemeChanged(final SchemeEvent event) {
		if (event == null) {
			throw new NullPointerException(
					"Cannot send a null event to listeners."); //$NON-NLS-1$
		}

		if (listeners == null) {
			return;
		}

		final Iterator listenerItr = listeners.iterator();
		while (listenerItr.hasNext()) {
			final ISchemeListener listener = (ISchemeListener) listenerItr
					.next();
			listener.schemeChanged(event);
		}
	}

	public final String getParentId() throws NotDefinedException {
		if (!defined) {
			throw new NotDefinedException(
					"Cannot get the parent identifier from an undefined scheme. "  //$NON-NLS-1$
					+ id);
		}

		return parentId;
	}

	public final void removeSchemeListener(final ISchemeListener schemeListener) {
		if (schemeListener == null) {
			throw new NullPointerException("Cannot remove a null listener."); //$NON-NLS-1$
		}

		if (listeners == null) {
			return;
		}

		listeners.remove(schemeListener);

		if (listeners.isEmpty()) {
			listeners = null;
		}
	}

	@Override
