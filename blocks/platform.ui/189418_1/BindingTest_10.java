	@Test
	public void test() {
		Catalog fruits = new Catalog("Fruits");
		List<CatalogItem> items;
		items = new ArrayList<>();
		items.add(new CatalogItem("Apple", new Content("Text 1")));
		items.add(new CatalogItem("Orange", new Content("Text 2")));
		fruits.setItems(items);

		Catalog vegetables = new Catalog("Vegetables");
		items = new ArrayList<>();
		items.add(new CatalogItem("Peas", new Content("Text 3")));
		items.add(new CatalogItem("Carrots", new Content("Text 4")));
		items.add(new CatalogItem("Potatoes", new Content("Text 5")));
		vegetables.setItems(items);

		Catalog foods = new Catalog("Foods");
		List<Catalog> catalogs = new ArrayList<>();
		catalogs.add(fruits);
		catalogs.add(vegetables);
		foods.setCatalogs(catalogs);

		items = new ArrayList<>();
		items.add(new CatalogItem("Own Hand", new Content("Text 6")));
		foods.setItems(items);

		catalogs = new ArrayList<>();
		catalogs.add(foods);
		fruits.setCatalogs(catalogs);

		var cat = new WritableValue<>(fruits, null);

		IObservableValue<String> value = Properties.convertedValue((Catalog c) -> c.getItems().get(0)) //
				.value(CatalogItem::getContent) //
				.value(Content::getText) //
				.observeDetail(cat);

		value.addValueChangeListener(e -> System.out.println(e.diff.getNewValue()));

		System.out.println("Value: " + value.getValue());

		cat.setValue(foods);

		System.out.println("Value: " + value.getValue());

	}

