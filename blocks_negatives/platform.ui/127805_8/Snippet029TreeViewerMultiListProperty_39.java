		items = new ArrayList();
		items.add(foods);
		catalog.setCatalogs(items);

		IListProperty childrenProperty = new MultiListProperty(
				new IListProperty[] { BeanProperties.list("catalogs"),
						BeanProperties.list("items") });

		ObservableListTreeContentProvider contentProvider = new ObservableListTreeContentProvider(
				childrenProperty.listFactory(), null);
