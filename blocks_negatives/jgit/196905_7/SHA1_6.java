	public byte[] digest() throws Sha1CollisionException {
		finish();

		byte[] b = new byte[20];
		NB.encodeInt32(b, 0, h.a);
		NB.encodeInt32(b, 4, h.b);
		NB.encodeInt32(b, 8, h.c);
		NB.encodeInt32(b, 12, h.d);
		NB.encodeInt32(b, 16, h.e);
		return b;
	}
