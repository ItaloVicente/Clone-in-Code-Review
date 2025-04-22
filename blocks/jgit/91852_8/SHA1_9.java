		id.set(h.a
	}

	public boolean hasCollision() {
		return foundCollision;
	}

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
			save(0x67452301
		}

		final void save(int a1
			a = a1;
			b = b1;
			c = c1;
			d = d1;
			e = e1;
		}

		ObjectId toObjectId() {
			return new ObjectId(a
		}
