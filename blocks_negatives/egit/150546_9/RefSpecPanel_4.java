			@SuppressWarnings("boxing")
			@Override
			protected void setValue(final Object element, final Object value) {
				final RefSpec oldSpec = (RefSpec) element;
				final RefSpec newSpec = oldSpec.setForceUpdate((Boolean) value);
				setRefSpec(oldSpec, newSpec);
			}
