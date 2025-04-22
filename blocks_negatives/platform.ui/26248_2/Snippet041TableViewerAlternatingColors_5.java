
		});

		MyModel[] model = createModel();
		v.setInput(model);
		v.getTable().setLinesVisible(true);
		v.getTable().setHeaderVisible(true);

		final ViewerFilter filter = new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				return ((MyModel)element).counter % 2 == 0;
			}

