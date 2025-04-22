		BufferedReader reader = null;
		try {
			StringBuilder buffer = new StringBuilder();
			char[] part = new char[2048];
			int read = 0;
			reader = new BufferedReader(new InputStreamReader(is, encoding));
