
	@Test
	public void shatteredCollision()
			throws IOException
		byte[] pdf1 = read("shattered-1.pdf"
		byte[] pdf2 = read("shattered-2.pdf"
		MessageDigest md;
		SHA1 s;

		ObjectId bad = ObjectId
				.fromString("38762cf7f55934b34d179ae6a4c80cadccbb7f0a");
		md = MessageDigest.getInstance("SHA-1");
		md.update(pdf1);
		assertEquals("shattered-1 collides"
				ObjectId.fromRaw(md.digest()));
		s = SHA1.newInstance().setDetectCollision(false);
		s.update(pdf1);
		assertEquals("shattered-1 collides"

		md = MessageDigest.getInstance("SHA-1");
		md.update(pdf2);
		assertEquals("shattered-2 collides"
				ObjectId.fromRaw(md.digest()));
		s = SHA1.newInstance().setDetectCollision(false);
		s.update(pdf2);
		assertEquals("shattered-2 collides"

		s = SHA1.newInstance().setDetectCollision(true);
		s.update(pdf1);
		try {
			s.digest();
			fail("expected " + Sha1CollisionException.class.getSimpleName());
		} catch (Sha1CollisionException e) {
			assertTrue("shattered-1 detected"
		}

		s = SHA1.newInstance().setDetectCollision(true);
		s.update(pdf2);
		try {
			s.digest();
			fail("expected " + Sha1CollisionException.class.getSimpleName());
		} catch (Sha1CollisionException e) {
			assertTrue("shattered-2 detected"
		}

		s = SHA1.newInstance().setDetectCollision(true).setSafeHash(true);
		s.update(pdf1);
		ObjectId safe1 = s.toObjectId();

		s = SHA1.newInstance().setDetectCollision(true).setSafeHash(true);
		s.update(pdf2);
		ObjectId safe2 = s.toObjectId();

		assertNotEquals("safe hashes not same"
		assertNotEquals("safe hash changed"
		assertNotEquals("safe hash changed"
		assertEquals(
				"shattered-1 matches sha1dc"
				ObjectId.fromString("16e96b70000dd1e7c85b8368ee197754400e58ec")
				safe1);
		assertEquals(
				"shattered-2 matches sha1dc"
				ObjectId.fromString("e1761773e6a35916d99f891b77663e6405313587")
				safe2);
	}

	private byte[] read(String name
		try (InputStream in = getClass().getResourceAsStream(name)) {
			ByteBuffer buf = IO.readWholeStream(in
			byte[] r = new byte[buf.remaining()];
			buf.get(r);
			return r;
		}
	}
