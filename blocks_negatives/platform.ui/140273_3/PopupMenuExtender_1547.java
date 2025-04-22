            @Override
			public void addSelectionChangedListener(
                    ISelectionChangedListener listener) {
                throw new UnsupportedOperationException(
                "This ISelectionProvider is static, and cannot be modified."); //$NON-NLS-1$
            }
