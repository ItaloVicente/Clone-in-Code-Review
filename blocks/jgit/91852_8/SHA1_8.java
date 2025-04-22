	  if (t == 58) {
		{ b += s3(c
		{ a += s3(b

		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b
	  }
		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b
		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b
		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b

		hTmp.save(hIn.a + a
	}

	private static int s1(int a
		return rotateLeft(a
				+ ((b & c) | ((~b) & d))
				+ 0x5A827999 + w_t;
	}

	private static int s2(int a
		return rotateLeft(a
				+ (b ^ c ^ d)
				+ 0x6ED9EBA1 + w_t;
	}

	private static int s3(int a
		return rotateLeft(a
				+ ((b & c) | (b & d) | (c & d))
				+ 0x8F1BBCDC + w_t;
	}

	private static int s4(int a
		return rotateLeft(a
				+ (b ^ c ^ d)
				+ 0xCA62C1D6 + w_t;
	}

	private static boolean eq(State q
		return q.a == r.a
				&& q.b == r.b
				&& q.c == r.c
				&& q.d == r.d
				&& q.e == r.e;
