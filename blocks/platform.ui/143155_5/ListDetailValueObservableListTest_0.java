		assertTrue(list.isDisposed());
	}

	@Test
	public void testNestedMultiListsAdd() {
		WritableList<SimplePerson> persons = new WritableList<>();

		IObservableList<SimpleOrder> orders = BeanProperties.list(SimplePerson.class, "orders", SimpleOrder.class)
				.observeDetail(persons);
		IObservableList<String> products = BeanProperties.list(SimpleOrder.class, "products", String.class)
				.observeDetail(orders);

		ListChangeEventTracker<?> changeTracker = ListChangeEventTracker.observe(products);

		assertTrue(products.isEmpty());


		SimplePerson p1 = new SimplePerson("name1", new SimpleOrder(1, "product111"));
		persons.add(p1);

		{
			assertEquals(Arrays.asList("product111"), products);
			assertEquals(p1.getOrders().get(0), orders.get(0));

			assertEquals(1, changeTracker.count);
			ListDiffEntry<?>[] entries = changeTracker.event.diff.getDifferences();
			assertEquals(1, entries.length);
			assertEquals(0, entries[0].getPosition());
			assertTrue(entries[0].isAddition());
		}

		SimplePerson p2 = new SimplePerson("name2", new SimpleOrder(2, "product211", "product212"));
		persons.add(p2);

		{
			assertEquals(p2.getOrders().get(0), orders.get(1));
			assertEquals(Arrays.asList("product111", "product211", "product212"), products);

			assertEquals(2, changeTracker.count);
			ListDiffEntry<?>[] entries = changeTracker.event.diff.getDifferences();
			assertEquals(2, entries.length);
			assertTrue(entries[0].isAddition());
			assertEquals(1, entries[0].getPosition());
			assertTrue(entries[1].isAddition());
			assertEquals(2, entries[1].getPosition());
		}

		SimplePerson p3 = new SimplePerson("name3", new SimpleOrder(3, "product311", "product312"));
		persons.add(1, p3);

		{
			assertEquals(Arrays.asList("product111", "product311", "product312", "product211", "product212"), products);
			assertEquals(p3.getOrders().get(0), orders.get(1));

			assertEquals(3, changeTracker.count);
			ListDiffEntry<?>[] entries = changeTracker.event.diff.getDifferences();
			assertEquals(2, entries.length);
			assertTrue(entries[0].isAddition());
			assertTrue(entries[0].isAddition());
			assertEquals(1, entries[0].getPosition());
			assertTrue(entries[1].isAddition());
			assertEquals(2, entries[1].getPosition());
		}
	}

	@Test
	public void testNestedMultiListsReplace() {
		WritableList<SimplePerson> persons = new WritableList<>();

		IObservableList<String> products = BeanProperties.list(SimplePerson.class, "orders", SimpleOrder.class)
				.list(BeanProperties.list("products", String.class)).observeDetail(persons);

		SimplePerson p1 = new SimplePerson("name1", new SimpleOrder(1, "product111"));
		SimplePerson p2 = new SimplePerson("name2", new SimpleOrder(2, "product211", "product212"));
		persons.add(p1);
		persons.add(p2);

		List<ListDiff<?>> diffs = new ArrayList<>();
		products.addListChangeListener(e -> diffs.add(e.diff));

		assertEquals(3, products.size());

		persons.set(0, new SimplePerson("name3", new SimpleOrder(3, "product311", "product312")));

		assertEquals(4, products.size());
		assertEquals(Arrays.asList("product311", "product312", "product211", "product212"), products);

		assertEquals(diffs.size(), 1);
		ListDiffEntry<?>[] entries = diffs.get(0).getDifferences();
		assertEquals(3, entries.length);
		assertEquals(0, entries[0].getPosition());
		assertEquals(0, entries[1].getPosition());
		assertEquals(1, entries[2].getPosition());
		assertFalse(entries[0].isAddition());
		assertTrue(entries[1].isAddition());
		assertTrue(entries[2].isAddition());
	}

	@Test
	public void testNestedMultiListsRemove() {
		WritableList<SimplePerson> persons = new WritableList<>();

		IObservableList<String> products = BeanProperties.list(SimplePerson.class, "orders", SimpleOrder.class)
				.list(BeanProperties.list("products", String.class)).observeDetail(persons);

		SimplePerson p1 = new SimplePerson("name1", new SimpleOrder(1, "product111"));
		SimplePerson p2 = new SimplePerson("name2", new SimpleOrder(2, "product211", "product212"));
		SimplePerson p3 = new SimplePerson("name3", new SimpleOrder(3, "product311", "product312"));
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);

		List<ListDiff<?>> diffs = new ArrayList<>();
		products.addListChangeListener(e -> diffs.add(e.diff));

		persons.remove(1);
		{
			assertEquals(Arrays.asList("product111", "product311", "product312"), products);
			assertEquals(diffs.size(), 1);
			ListDiffEntry<?>[] entries = diffs.get(0).getDifferences();
			assertEquals(2, entries.length);
			assertEquals(1, entries[0].getPosition());
			assertEquals(2, entries[1].getPosition());
			assertFalse(entries[0].isAddition());
			assertFalse(entries[1].isAddition());
		}

		persons.remove(0);
		{
			assertEquals(Arrays.asList("product311", "product312"), products);
			assertEquals(diffs.size(), 2);
			ListDiffEntry<?>[] entries = diffs.get(1).getDifferences();
			assertEquals(1, entries.length);
			assertEquals(0, entries[0].getPosition());
			assertFalse(entries[0].isAddition());
		}

		persons.remove(0);
		{
			assertTrue(products.isEmpty());
			assertEquals(diffs.size(), 3);
			ListDiffEntry<?>[] entries = diffs.get(2).getDifferences();
			assertEquals(2, entries.length);
			assertEquals(0, entries[0].getPosition());
			assertEquals(1, entries[1].getPosition());
			assertFalse(entries[0].isAddition());
			assertFalse(entries[1].isAddition());
		}
