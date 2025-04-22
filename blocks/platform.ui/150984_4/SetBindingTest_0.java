	@Test
	public void testErrorDuringConversion() {
		UpdateSetStrategy<String, String> modelToTarget = new UpdateSetStrategy<>();
		modelToTarget.setConverter(IConverter.create(String.class, String.class, fromObject -> {
			throw new IllegalArgumentException();
		}));

		Binding binding = dbc.bindSet(target, model, new UpdateSetStrategy<>(), modelToTarget);
		CountDownLatch latch = new CountDownLatch(1);

		Policy.setLog(status -> {
			latch.countDown();
			assertEquals(IStatus.ERROR, status.getSeverity());
			assertTrue(status.getException() instanceof IllegalArgumentException);
		});

		model.add("first");

		assertTrue("Target not changed on conversion error", target.isEmpty());
		assertEquals(0, latch.getCount());
		assertEquals(IStatus.ERROR, binding.getValidationStatus().getValue().getSeverity());

		Policy.setLog(null);
	}

	@Test
	public void testErrorDuringRemoveIsLogged() {
		IObservableSet<String> target = new WritableSet<String>() {
			@Override
			public boolean remove(Object elem) {
				throw new IllegalArgumentException();
			}
		};

		Binding binding = dbc.bindSet(target, model, new UpdateSetStrategy<>(), new UpdateSetStrategy<>());
		CountDownLatch latch = new CountDownLatch(1);

		Policy.setLog(status -> {
			latch.countDown();
			assertEquals(IStatus.ERROR, status.getSeverity());
			assertTrue(status.getException() instanceof IllegalArgumentException);
		});

		model.add("first");
		model.remove("first");

		assertEquals(0, latch.getCount());
		assertEquals(IStatus.ERROR, binding.getValidationStatus().getValue().getSeverity());
	}

