		public void putDefault(String key, FileEditorMapping value) {
			defaultMap.put(key, value);
		}

		public void put(String key, FileEditorMapping value) {
			Object result = defaultMap.get(key);
			if (value.equals(result)) {
