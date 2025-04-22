		while (s.remaining() > 0 && !inf.finished()) {
			if (inf.needsInput()) {
				final int n = Math.min(s.remaining(), tmp.length);
				s.get(tmp, 0, n);
				inf.setInput(tmp, 0, n);
			}
			o += inf.inflate(b, o, b.length - o);
		}
		while (!inf.finished() && !inf.needsInput())
			o += inf.inflate(b, o, b.length - o);
		return o;
