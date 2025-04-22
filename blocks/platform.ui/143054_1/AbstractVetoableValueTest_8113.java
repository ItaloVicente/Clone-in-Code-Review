	private static class VetoableValueStub extends AbstractVetoableValue {
		VetoableValueStub() {
			this(Realm.getDefault());
		}

		VetoableValueStub(Realm realm) {
			super(realm);
		}
