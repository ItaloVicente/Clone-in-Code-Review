		save(hIn
		a = s[0]; b = s[1]; c = s[2]; d = s[3]; e = s[4];

		if (t <= 58) { b += sr3(c
		if (t <= 59) { a += sr3(b

		if (t <= 60) { e += sr4(a
		if (t <= 61) { d += sr4(e
		if (t <= 62) { c += sr4(d
		if (t <= 63) { b += sr4(c
		if (t <= 64) { a += sr4(b
		if (t <= 65) { e += sr4(a
		if (t <= 66) { d += sr4(e
		if (t <= 67) { c += sr4(d
		if (t <= 68) { b += sr4(c
		if (t <= 69) { a += sr4(b
		if (t <= 70) { e += sr4(a
		if (t <= 71) { d += sr4(e
		if (t <= 72) { c += sr4(d
		if (t <= 73) { b += sr4(c
		if (t <= 74) { a += sr4(b
		if (t <= 75) { e += sr4(a
		if (t <= 76) { d += sr4(e
		if (t <= 77) { c += sr4(d
		if (t <= 78) { b += sr4(c
		if (t <= 79) { a += sr4(b

		save(hOut
	}

	private int s1(int a
				+ ((b & c) | ((~b) & d)) + 0x5A827999 + w[t];
	}

	private int s2(int a
				+ (b ^ c ^ d) + 0x6ED9EBA1 + w[t];
	}

	private int s3(int a
				+ ((b & c) | (b & d) | (c & d)) + 0x8F1BBCDC + w[t];
	}

	private int sr3(int a
				+ ((b & c) | (b & d) | (c & d)) + 0x8F1BBCDC + w2[t];
	}

	private int s4(int a
				+ (b ^ c ^ d) + 0xCA62C1D6 + w[t];
	}

	private int sr4(int a
				+ (b ^ c ^ d) + 0xCA62C1D6 + w2[t];
	}

	private static int s_30(int b) {
		return (b << 30) | (b >>> 2);
	}

	private static int r_30(int x) {
		return (x >>> 30) | (x << 2);
	}

	private int r1(int a
				+ ((b & c) | ((~b) & d)) + 0x5A827999 + w2[t];
	}

	private int r2(int a
				+ (b ^ c ^ d) + 0x6ED9EBA1 + w2[t];
	}

	private int r3(int a
				+ ((b & c) | (b & d) | (c & d)) + 0x8F1BBCDC + w2[t];
	}

	private int r4(int a
				+ (b ^ c ^ d) + 0xCA62C1D6 + w2[t];
	}

	private static void save(int[] h
		h[0] = a;
		h[1] = b;
		h[2] = c;
		h[3] = d;
		h[4] = e;
	}

	private static boolean eq(int[] a
		return a[0] == b[0]
				&& a[1] == b[1]
				&& a[2] == b[2]
				&& a[3] == b[3]
				&& a[4] == b[4];
