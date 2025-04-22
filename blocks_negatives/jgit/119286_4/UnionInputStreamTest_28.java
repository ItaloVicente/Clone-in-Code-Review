		u.add(new ByteArrayInputStream(new byte[] { 1 }) {
			@Override
			public void close() {
				closed[0] = true;
			}
		});
		u.add(new ByteArrayInputStream(new byte[] { 2 }) {
			@Override
			public void close() {
				closed[1] = true;
			}
		});
