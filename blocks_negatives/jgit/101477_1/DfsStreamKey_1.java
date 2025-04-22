		@Override
		public DfsStreamKey derive(String suffix) {
			byte[] s = suffix.getBytes(UTF_8);
			byte[] n = Arrays.copyOf(name, name.length + s.length);
			System.arraycopy(s, 0, n, name.length, s.length);
			return new ByteArrayDfsStreamKey(n);
		}

