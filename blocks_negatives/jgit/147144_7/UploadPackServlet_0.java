		SmartOutputStream out = new SmartOutputStream(req, rsp, false) {
			@Override
			public void flush() throws IOException {
				doFlush();
			}
		};
