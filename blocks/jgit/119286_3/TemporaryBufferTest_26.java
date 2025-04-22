				try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
					b.writeTo(o
					final byte[] r = o.toByteArray();
					assertEquals(expect.length
					assertArrayEquals(expect
				}
