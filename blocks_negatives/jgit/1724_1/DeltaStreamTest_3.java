		return new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return base.length;
			}

			@Override
			protected InputStream openBase() throws IOException {
				return new ByteArrayInputStream(base);
			}
		};
