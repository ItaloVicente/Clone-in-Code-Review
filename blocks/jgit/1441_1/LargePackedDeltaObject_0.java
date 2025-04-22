
			@Override
			public void close() throws IOException {
				super.close();
				if (buffer != null)
					buffer.destroy();
			}
