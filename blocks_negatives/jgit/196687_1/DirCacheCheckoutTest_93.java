			assertNotNull("found unexpected entry for path " + path
					+ " in index", expectedValue);
			assertTrue("unexpected content for path " + path
					+ " in index. Expected: <" + expectedValue + ">",
					Arrays.equals(db.open(read.getEntry(j).getObjectId())
							.getCachedBytes(), i.get(path).getBytes(UTF_8)));
