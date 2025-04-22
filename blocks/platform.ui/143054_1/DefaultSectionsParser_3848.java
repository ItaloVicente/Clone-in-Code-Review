		Hashtable<String, MarkElement> markTable = new Hashtable<>(40);
		ArrayList<MarkElement> topLevel = new ArrayList<>();
		String s = getText(file);
		int start = 0;
		int end = -1;
		int lineno = 0;
		int lastlineno = 0;
		MarkElement lastme = null;
		int ix;
