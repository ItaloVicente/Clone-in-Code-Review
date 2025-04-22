			@Override
			protected void setValue(Object element, Object value) {
				((MyModel) element).counter = Integer
						.parseInt(value.toString());
				v.update(element, null);
			}
		});
