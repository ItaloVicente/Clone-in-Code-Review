			last = curr;
		}
		if (last == '\r') {
			if (complete) {
				return false;
			}
