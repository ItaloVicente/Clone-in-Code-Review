		IObservableValue<String> value = BeanProperties.value(Catalog.class, "catalogs", Catalog.class) //
				.value(Catalog::getItem) //
				.value(CatalogItem::getContent) //
				.value(Content::getText) //
				.observe(catalog);

		var contentProvider = new ObservableListTreeContentProvider<>(
