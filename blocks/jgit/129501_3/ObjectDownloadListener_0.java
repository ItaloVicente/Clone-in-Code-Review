			try {
				buffer.clear();
				if (in.read(buffer) < 0) {
					buffer = null;
				} else {
					buffer.flip();
				}
			} catch(Throwable t) {
				LOG.log(Level.SEVERE
				buffer = null;
			} finally {
				if (buffer != null) {
					outChannel.write(buffer);
				} else {
					try {
						out.close();
					} finally {
