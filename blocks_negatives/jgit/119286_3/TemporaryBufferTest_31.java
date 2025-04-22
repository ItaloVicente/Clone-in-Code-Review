				final ByteArrayOutputStream o = new ByteArrayOutputStream();
				b.writeTo(o, null);
				o.close();
				final byte[] r = o.toByteArray();
				assertEquals(expect.length, r.length);
				assertArrayEquals(expect, r);
