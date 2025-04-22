		private static String stackTrace() {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			new Exception().printStackTrace(pw);
		}

