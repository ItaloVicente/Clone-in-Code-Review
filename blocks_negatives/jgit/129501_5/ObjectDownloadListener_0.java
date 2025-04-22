			if (in.read(buffer) != -1) {
				buffer.flip();
				outChannel.write(buffer);
				buffer.compact();
			} else {
				in.close();
				buffer.flip();
				while (out.isReady()) {
					if (buffer.hasRemaining()) {
						outChannel.write(buffer);
					} else {
