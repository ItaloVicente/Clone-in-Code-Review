			if (buffer.hasRemaining()) {

				if (in.read(buffer) == -1) {
					inEof = true;
					in.close();
				}
