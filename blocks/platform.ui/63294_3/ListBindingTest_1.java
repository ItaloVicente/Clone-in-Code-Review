
	public void testErrorDuringConversionIsLogged() {
		UpdateListStrategy modelToTarget = new UpdateListStrategy();
		modelToTarget.setConverter(new IConverter() {

			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Object getToType() {
				return String.class;
			}

			@Override
			public Object convert(Object fromObject) {
				throw new IllegalArgumentException();
			}

		});

		dbc.bindList(target, model, new UpdateListStrategy(), modelToTarget);
		CountDownLatch latch = new CountDownLatch(1);

		Policy.setLog(new ILogger() {

			@Override
			public void log(IStatus status) {
				latch.countDown();
				Assert.assertEquals(IStatus.ERROR, status.getSeverity());
				Assert.assertTrue(status.getException() instanceof IllegalArgumentException);
			}
		});

		model.add("first");

		Assert.assertEquals(0, latch.getCount());
	}

	public void testErrorDuringRemoveIsLogged() {
		IObservableList<String> target = new WritableList<String>() {
			@Override
			public String remove(int index) {
				throw new IllegalArgumentException();
			}
		};
		dbc.bindList(target, model, new UpdateListStrategy(), new UpdateListStrategy());
		CountDownLatch latch = new CountDownLatch(1);

		Policy.setLog(new ILogger() {

			@Override
			public void log(IStatus status) {
				latch.countDown();
				Assert.assertEquals(IStatus.ERROR, status.getSeverity());
				Assert.assertTrue(status.getException() instanceof IllegalArgumentException);
			}
		});

		model.add("first");
		model.remove("first");

		Assert.assertEquals(0, latch.getCount());
	}

	public void testErrorDuringMoveIsLogged() {
		IObservableList<String> target = new WritableList<String>() {
			@Override
			public String move(int index, int index2) {
				throw new IllegalArgumentException();
			}
		};
		dbc.bindList(target, model, new UpdateListStrategy(), new UpdateListStrategy());
		CountDownLatch latch = new CountDownLatch(1);

		Policy.setLog(new ILogger() {

			@Override
			public void log(IStatus status) {
				latch.countDown();
				Assert.assertEquals(IStatus.ERROR, status.getSeverity());
				Assert.assertTrue(status.getException() instanceof IllegalArgumentException);
			}
		});

		model.add("first");
		model.add("second");
		model.move(0, 1);

		Assert.assertEquals(0, latch.getCount());
	}

	public void testErrorDuringReplaceIsLogged() {
		IObservableList<String> target = new WritableList<String>() {
			@Override
			public String set(int index, String element) {
				throw new IllegalArgumentException();
			}
		};
		dbc.bindList(target, model, new UpdateListStrategy(), new UpdateListStrategy());
		CountDownLatch latch = new CountDownLatch(1);

		Policy.setLog(new ILogger() {

			@Override
			public void log(IStatus status) {
				latch.countDown();
				Assert.assertEquals(IStatus.ERROR, status.getSeverity());
				Assert.assertTrue(status.getException() instanceof IllegalArgumentException);
			}
		});

		model.add("first");
		model.set(0, "second");

		Assert.assertEquals(0, latch.getCount());
	}
