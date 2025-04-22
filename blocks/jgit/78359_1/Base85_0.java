	static char enc85[] = { '0'
			'A'
			'K'
			'U'
			'e'
			'o'
			'y'
			'+'
			'`'
	};

	static int dec85[] = new int[256];

	static {
		for (int i = 0; i < dec85.length; i++) {
			dec85[i] = -1;
		}
		for (int i = 0; i < enc85.length; i++) {
			char c = enc85[i];
