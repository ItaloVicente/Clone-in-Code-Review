			archive(git
				put("compression-level"
			}});
			int sizeCompression9 = getNumBytes(archive);

			assertTrue(sizeCompression1 >= sizeCompression9);
