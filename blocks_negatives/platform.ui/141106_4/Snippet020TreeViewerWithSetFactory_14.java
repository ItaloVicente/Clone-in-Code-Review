				new ComputedValue(Boolean.TYPE) {
					@Override
					protected Object calculate() {
						return Boolean.valueOf(clipboard.getValue() != null);
					}
				});
