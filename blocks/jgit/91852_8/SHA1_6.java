		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b
		state65.save(a
		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b
		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b
		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b

		h.save(h.a + a
	}

	private void recompress(int t) {
		State s;
		if (t == 58) {
			s = state58;
		} else if (t == 65) {
			s = state65;
		} else {
			throw new IllegalStateException();
