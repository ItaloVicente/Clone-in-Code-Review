            @Override
			protected void doSetApprovedValue(Object value) {
                count++;
                this.value = value;
            }
        }

        Realm realm = new CurrentRealm(true);
        VetoableValue vetoableValue = new VetoableValue(realm);
        assertEquals(0, vetoableValue.count);
        assertEquals(null, vetoableValue.value);

        Object value = new Object();
        vetoableValue.setValue(value);
        assertEquals(1, vetoableValue.count);
        assertEquals(value, vetoableValue.value);
    }

    @Test
	public void testFireValueChangeRealmChecks() throws Exception {
    	RealmTester.exerciseCurrent(new Runnable() {
