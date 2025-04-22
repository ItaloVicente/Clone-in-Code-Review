			@Override
			public String[] getProperties() {
				return null;
			}

			@Override
			public Object getProperty(String property) {
				return null;
			}
		};
		return new DecoratingStyledCellLabelProvider(new TestCellLabelProvider(useColor),
				useColor ? getDecorator() : null, context);
