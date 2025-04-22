		ViewerSupport.bind(ordersViewer, BeanProperties
				.list((Class) selectedPerson.getValueType(),
						"orders", SimpleOrder.class).observeDetail(selectedPerson),
				BeanProperties
				.values(SimpleOrder.class,
						new String[] { "orderNumber", "date" }));
