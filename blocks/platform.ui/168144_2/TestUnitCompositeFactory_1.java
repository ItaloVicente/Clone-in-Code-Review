	@Test
	public void createsCompositeWithLayoutSupplier() {
		GridLayoutFactory gridLayoutFactory = GridLayoutFactory.fillDefaults();
		Composite composite = CompositeFactory.newComposite(SWT.MULTI).supplyLayout(gridLayoutFactory::create)
				.create(shell);

		assertEquals(GridLayout.class, composite.getLayout().getClass());
	}

