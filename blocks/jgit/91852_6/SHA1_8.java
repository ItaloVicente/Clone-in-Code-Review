		 e += s3(a
		 d += s3(e
		 c += s3(d
		 b += s3(c
		 a += s3(b
		 e += s3(a
		 d += s3(e
		 c += s3(d
		 b += s3(c
		 a += s3(b
		 e += s3(a
		 d += s3(e
		 c += s3(d
		 b += s3(c
		 a += s3(b
		 e += s3(a
		 d += s3(e
		 c += s3(d
		state58.save(a
		 b += s3(c
		 a += s3(b

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
