		cnt = 0;
		while (cnt < buf.length) {
			int n = in.read(buf
			if (n < 0) {
				break;
			}
			cnt += n;
		}
		if (cnt < 1) {
			cnt = -1;
