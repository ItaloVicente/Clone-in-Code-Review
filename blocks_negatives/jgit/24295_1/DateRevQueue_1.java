	public void add(final RevCommit c) {
		sinceLastIndex++;
		if (++inQueue > REBUILD_INDEX_COUNT
				&& sinceLastIndex > REBUILD_INDEX_COUNT)
			buildIndex();

		Entry q = head;
		final long when = c.commitTime;

		if (first <= last && index[first].commit.commitTime > when) {
			int low = first, high = last;
			while (low <= high) {
				int mid = (low + high) >>> 1;
				int t = index[mid].commit.commitTime;
				if (t < when)
					high = mid - 1;
				else if (t > when)
					low = mid + 1;
				else {
					low = mid - 1;
					break;
				}
			}
			low = Math.min(low, high);
			while (low > first && when == index[low].commit.commitTime)
				--low;
			q = index[low];
		}

		final Entry n = newEntry(c);
		if (q == null || (q == head && when > q.commit.commitTime)) {
			n.next = q;
			head = n;
		} else {
			Entry p = q.next;
			while (p != null && p.commit.commitTime >= when) {
				q = p;
				p = q.next;
			}
			n.next = q.next;
			q.next = n;
		}
	}

	public RevCommit next() {
		final Entry q = head;
		if (q == null)
			return null;

		if (index != null && q == index[first])
			index[first++] = null;
		inQueue--;

		head = q.next;
		freeEntry(q);
		return q.commit;
	}

	private void buildIndex() {
		sinceLastIndex = 0;
		first = 0;
		index = new Entry[inQueue / 100 + 1];
		int qi = 0, ii = 0;
		for (Entry q = head; q != null; q = q.next) {
			if (++qi % 100 == 0)
				index[ii++] = q;
		}
		last = ii - 1;
