	public void print(Sequence s, int begin, int end) {
		RawText raw = (RawText)s;
		try {
			while (begin < end) {
				System.err.print("" + begin + ": ");
				raw.writeLine(System.err, begin++);
				System.err.println("");
			}
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void print(int beginA, int endA, int beginB, int endB) {
		System.err.println("<<<<<<");
		print(a, beginA, endA);
		System.err.println("======");
		print(b, beginB, endB);
		System.err.println(">>>>>>");
	}

