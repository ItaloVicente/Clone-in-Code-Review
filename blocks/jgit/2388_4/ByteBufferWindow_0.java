	@Override
	void write(PackOutputStream out
		final ByteBuffer s = buffer.slice();
		s.position((int) (pos - start));

		while (0 < cnt) {
			byte[] buf = out.getCopyBuffer();
			int n = Math.min(cnt
			s.get(buf
			out.write(buf
			cnt -= n;
		}
	}

