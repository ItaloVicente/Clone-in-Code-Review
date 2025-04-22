				try (ByteArrayOutputStream hookOutBytes = new ByteArrayOutputStream();
						ByteArrayOutputStream hookErrBytes = new ByteArrayOutputStream();
						PrintStream stdout = new PrintStream(hookOutBytes, true,
								hookCharset);
						PrintStream stderr = new PrintStream(hookErrBytes, true,
								hookCharset)) {
