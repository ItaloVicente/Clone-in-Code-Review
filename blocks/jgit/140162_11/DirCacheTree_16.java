		if (aLen == bLen) {
			return 0;
		}
		if (aLen < bLen) {
			return '/' - (b[cPos] & 0xff);
		}
		return (a[cPos] & 0xff) - '/';
