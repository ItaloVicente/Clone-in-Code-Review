		final TemporaryBuffer.Heap b = new TemporaryBuffer.Heap(cnt + 4);
		b.write(test);
		b.close();

		InputStream in = b.openInputStream();
		byte[] act = new byte[cnt];
		IO.readFully(in, act, 0, cnt);
		assertArrayEquals(test, act);
