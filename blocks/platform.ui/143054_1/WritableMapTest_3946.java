		RealmTester.exerciseCurrent(() -> {
			WritableMap map = new WritableMap();
			CurrentRealm realm = (CurrentRealm) Realm.getDefault();
			boolean current = realm.isCurrent();
			realm.setCurrent(true);
			map.put("", "");
			realm.setCurrent(current);

			map.remove("");
