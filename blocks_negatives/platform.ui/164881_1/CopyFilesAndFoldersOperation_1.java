		IOverwriteQuery query = new IOverwriteQuery() {
			@Override
			public String queryOverwrite(String pathString) {
				if (alwaysOverwrite) {
					return ALL;
				}
