		try (final TemporaryBuffer.Heap b = new TemporaryBuffer.Heap(cnt + 4)) {
			b.write(test);
			InputStream in = b.openInputStream();
			byte[] act = new byte[cnt];
			IO.readFully(in
			assertArrayEquals(test
		}
