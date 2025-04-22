		Bean input0 = new Bean(Arrays.asList(new Bean("elem0"), new Bean("elem1"), new Bean("elem2")));
		Bean input1 = new Bean(Arrays.asList(new Bean("elem3"), new Bean("elem4"), new Bean("elem5")));

		@SuppressWarnings({ "unchecked", "rawtypes" })
		IValueProperty<Object, String> labelProp = BeanProperties.value((Class) Bean.class, "value");
		@SuppressWarnings({ "unchecked", "rawtypes" })
		IListProperty<Object, Object> childrenProp = BeanProperties.list((Class) Bean.class, "list");
