		deflater.setInput(data, 0, data.length);
		deflater.finish();

		byte[] buf = out.getCopyBuffer();
		do {
			final int n = deflater.deflate(buf, 0, buf.length);
			if (n > 0)
				out.write(buf, 0, n);
		} while (!deflater.finished());
