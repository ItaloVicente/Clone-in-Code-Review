				new ComputedValue(Boolean.TYPE) {
					@Override
					protected Object calculate() {
						return Boolean.valueOf(clipboard.getValue() != null);
					}
				});

		ViewerSupport.bind(beanViewer, input, BeanProperties.list("list",
				Bean.class), BeanProperties.value(Bean.class, "text"));
