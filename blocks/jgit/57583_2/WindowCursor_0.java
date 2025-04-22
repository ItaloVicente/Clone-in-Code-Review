	static String getStackTraceAsString(Throwable throwable) {
	    StringWriter stringWriter = new StringWriter();
	    throwable.printStackTrace(new PrintWriter(stringWriter));
	    return stringWriter.toString();
	}

