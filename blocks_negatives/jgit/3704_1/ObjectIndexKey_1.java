		copyTo(r, 12);
		format32(r, 3, repo);
		r[11] = '.';
		r[2] = '.';
		r[1] = r[12 + 1];
		r[0] = r[12 + 0];
