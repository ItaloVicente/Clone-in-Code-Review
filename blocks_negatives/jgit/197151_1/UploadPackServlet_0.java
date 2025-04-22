			UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER);
			@SuppressWarnings("resource")
			SmartOutputStream out = new SmartOutputStream(req, rsp, false) {
				@Override
				public void flush() throws IOException {
					doFlush();
				}
			};
