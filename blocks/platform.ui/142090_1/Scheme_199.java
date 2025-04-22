	private Set listeners = null;

	private String parentId = null;

	Scheme(final String id) {
		super(id);
	}

	public final void addSchemeListener(final ISchemeListener schemeListener) {
		if (schemeListener == null) {
			throw new NullPointerException("Can't add a null scheme listener."); //$NON-NLS-1$
		}

		if (listeners == null) {
			listeners = new HashSet();
		}

		listeners.add(schemeListener);
	}

	@Override
