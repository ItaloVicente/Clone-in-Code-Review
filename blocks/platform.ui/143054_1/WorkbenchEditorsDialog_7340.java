			Adapter adapter = (Adapter) another;
			int result = collator.compare(getText()[sortColumn], adapter.getText()[sortColumn]);
			if (result == 0) {
				int column = sortColumn == 0 ? 1 : 0;
				result = collator.compare(getText()[column], adapter.getText()[column]);
			}
			if (reverse) {
