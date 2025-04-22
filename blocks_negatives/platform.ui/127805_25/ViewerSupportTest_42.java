		Bean input0 = new Bean(new HashSet<Bean>(Arrays.asList(new Bean[] { new Bean("elem0"), new Bean("elem1"),
				new Bean("elem2") })));
		Bean input1 = new Bean(new HashSet<Bean>(Arrays.asList(new Bean[] { new Bean("elem3"), new Bean("elem4"),
				new Bean("elem5") })));
		IValueProperty labelProp = BeanProperties.value(Bean.class, "value");
		ISetProperty childrenProp = BeanProperties.set(Bean.class, "set");
