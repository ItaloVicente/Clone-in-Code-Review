		String s = getPath();
		String[] elements;
		if ("file".equals(scheme) || LOCAL_FILE.matcher(s).matches()) {
			elements = s.split("[\\" + File.separator + "/]");
		} else
			elements = s.split("/");
