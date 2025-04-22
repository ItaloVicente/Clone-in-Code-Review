			public Object getAdapter(Object adaptableObject, Class adapterType) {
				return new IShowInSource() {
					@Override
					public ShowInContext getShowInContext() {
						return new ShowInContext(StructuredSelection.EMPTY,
								StructuredSelection.EMPTY);
					}
				};
