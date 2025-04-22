			final SmartOutputStream out = new SmartOutputStream(req, rsp) {
				@Override
				public void flush() throws IOException {
					doFlush();
				}
			};
