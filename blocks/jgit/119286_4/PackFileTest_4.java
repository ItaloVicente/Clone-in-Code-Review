			try (ObjectStream in = ol.openStream()) {
				assertNotNull("have stream"
				assertEquals(Constants.OBJ_BLOB
				assertEquals(data3.length
				byte[] act = new byte[data3.length];
				IO.readFully(in
				assertTrue("same content"
				assertEquals("stream at EOF"
			}
