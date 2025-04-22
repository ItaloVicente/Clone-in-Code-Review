	@Test
	public void testErrorDuringConversion() {
		UpdateValueStrategy<String, String> modelToTarget = new UpdateValueStrategy<>();
		modelToTarget.setConverter(IConverter.create(String.class, String.class, fromObject -> {
			throw new IllegalArgumentException();
		}));

		Binding binding = dbc.bindValue(target, model, new UpdateValueStrategy<>(), modelToTarget);
		CountDownLatch latch = new CountDownLatch(1);

		Policy.setLog(status -> {
			latch.countDown();
			assertEquals(IStatus.ERROR, status.getSeverity());
			assertTrue(status.getException() instanceof IllegalArgumentException);
		});

		model.setValue("first");

		assertNull("Target not changed on conversion error", target.getValue());
		assertEquals(0, latch.getCount());
		assertEquals(IStatus.ERROR, binding.getValidationStatus().getValue().getCode());

		Policy.setLog(null);
	}

