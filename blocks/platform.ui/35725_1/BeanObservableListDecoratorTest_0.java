		propertyDescriptor = new PropertyDescriptor("list", Bean.class,
				"getList", "setList");

		observableList = BeanProperties.list(bean.getClass(), "list", null)
				.observe(DisplayRealm.getRealm(Display.getDefault()), bean);

		decorator = new BeanObservableListDecorator(observableList,
				propertyDescriptor);
