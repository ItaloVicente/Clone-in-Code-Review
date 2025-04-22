			int read = 0;
			for (int readNow = cis2.read(buffer, 0, buffer.length); readNow != -1
					&& read < expected.length; readNow = cis2.read(buffer, 0,
					buffer.length)) {
				for (int index2 = 0; index2 < readNow; index2++) {
					assertEquals(expected[read + index2], buffer[index2]);
