			out.flush();
			in.close();
			out.close();
			byte[] actualBytes = out.toByteArray();
			Assert.assertEquals("bufsize=" + i, encode(expectBytes),
					encode(actualBytes));
