				final ByteArrayOutputStream o = new ByteArrayOutputStream();
				b.writeTo(o, null);
				o.close();
				final byte[] r = o.toByteArray();
				assertEquals(1, r.length);
				assertEquals(test, r[0]);
