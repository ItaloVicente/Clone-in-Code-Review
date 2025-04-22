				final ByteArrayOutputStream o = new ByteArrayOutputStream();
				b.writeTo(o, null);
				o.close();
				final byte[] r = o.toByteArray();
				assertEquals(test.length, r.length);
				assertArrayEquals(test, r);
