			out.flush();
			in.close();
			out.close();
			byte[] actualBytes = bos.toByteArray();
			Assert.assertEquals("bufsize=" + size, encode(expectBytes),
					encode(actualBytes));
