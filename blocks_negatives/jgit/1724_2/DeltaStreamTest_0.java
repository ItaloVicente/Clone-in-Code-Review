		final boolean[] opened = new boolean[1];
		in = new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return base.length;
			}

			@Override
			protected InputStream openBase() throws IOException {
				opened[0] = true;
				return new ByteArrayInputStream(base);
			}
		};
