	public SHA1 reset() {
		h.init();
		length = 0;
		foundCollision = false;
		return this;
	}

	private static final class State {
		int a;
		int b;
		int c;
		int d;
		int e;

		final void init() {
			save(0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0);
		}

		final void save(int a1, int b1, int c1, int d1, int e1) {
			a = a1;
			b = b1;
			c = c1;
			d = d1;
			e = e1;
		}

		ObjectId toObjectId() {
			return new ObjectId(a, b, c, d, e);
		}
	}
}
