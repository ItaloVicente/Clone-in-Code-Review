				in.close();
				buffer.flip();
				while (out.isReady()) {
					if (buffer.hasRemaining()) {
						outChannel.write(buffer);
					} else {
						context.complete();
						return;
					}
				}
