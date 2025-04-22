		int t = e - n;
		int h = toSearch[t].getPathHash();
		while (cur < t) {
			if (h == toSearch[t - 1].getPathHash())
				t--;
			else
				break;
