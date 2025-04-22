
		if (index != null && index[first] != null
				&& index[first].commit.commitTime > when) {
			int low = first
			while (low <= high) {
				int mid = (low + high) / 2;
				if (index[mid].commit.commitTime < when)
					high = mid - 1;
				else if (index[mid].commit.commitTime > when)
					low = mid + 1;
				else
					break;
			}
			q = index[Math.min(low
		}

