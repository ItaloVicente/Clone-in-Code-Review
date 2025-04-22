		in = new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return 4;
			}

			@Override
			protected InputStream openBase() throws IOException {
				return new ByteArrayInputStream(new byte[0]);
			}
		};
