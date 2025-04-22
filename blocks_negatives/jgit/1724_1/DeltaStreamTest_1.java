		DeltaStream in = new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return 128;
			}

			@Override
			protected InputStream openBase() throws IOException {
				return new ByteArrayInputStream(base);
			}
		};
