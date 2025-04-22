
		if (index != null && index[first] != null
				&& index[first].commit.commitTime > when) {
			int low = first
			while (low <= high) {
				int mid = (low + high) >>> 1;
				int t = index[mid].commit.commitTime;
				if (t < when)
					high = mid - 1;
				else if (t > when) {
					low = mid + 1;
				} else {
					low = mid - 1;
					break;
				}
			}
			low = Math.min(low
			while (low >= first && when == index[low].commit.commitTime)
				--low;
			q = index[low];
		}

