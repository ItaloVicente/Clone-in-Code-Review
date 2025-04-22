				try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
					b.writeTo(o
					final byte[] r = o.toByteArray();
					assertEquals(test.length
					assertArrayEquals(test
				}
