		hIn.save(a
		a = s.a; b = s.b; c = s.c; d = s.d; e = s.e;

	  if (t == 58) {
		{ b += sr3(c
		{ a += sr3(b

		{ e += sr4(a
		{ d += sr4(e
		{ c += sr4(d
		{ b += sr4(c
		{ a += sr4(b
	  }
		{ e += sr4(a
		{ d += sr4(e
		{ c += sr4(d
		{ b += sr4(c
		{ a += sr4(b
		{ e += sr4(a
		{ d += sr4(e
		{ c += sr4(d
		{ b += sr4(c
		{ a += sr4(b
		{ e += sr4(a
		{ d += sr4(e
		{ c += sr4(d
		{ b += sr4(c
		{ a += sr4(b

		hTmp.save(hIn.a + a
	}

	private int s1(int a
		return rotateLeft(a
	}

	private int s2(int a
		return rotateLeft(a
	}

	private int s3(int a
		return rotateLeft(a
	}

	private int sr3(int a
		return rotateLeft(a
	}

	private int s4(int a
		return rotateLeft(a
	}

	private int sr4(int a
		return rotateLeft(a
	}

	private int r1(int a
		return rotateLeft(a
	}

	private int r2(int a
		return rotateLeft(a
	}

	private int r3(int a
		return rotateLeft(a
	}

	private int r4(int a
		return rotateLeft(a
	}

	private static int f1(int b
		return (b & c) | ((~b) & d);
	}

	private static int f2(int b
		return b ^ c ^ d;
	}

	private static int f3(int b
		return (b & c) | (b & d) | (c & d);
	}

	private static boolean eq(State q
		return q.a == r.a
				&& q.b == r.b
				&& q.c == r.c
				&& q.d == r.d
				&& q.e == r.e;
