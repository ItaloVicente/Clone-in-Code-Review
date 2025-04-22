	@Test
	public void testFireValueChangeRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(() -> {
			VetoableValueStub observable = new VetoableValueStub();
			observable.fireValueChanging(null);
		});
	}
