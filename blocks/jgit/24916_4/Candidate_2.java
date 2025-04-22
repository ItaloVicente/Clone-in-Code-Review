	boolean canMergeRegions(Candidate other) {
		return sourceCommit == other.sourceCommit
				&& sourcePath.getPath().equals(other.sourcePath.getPath());
	}

	void mergeRegions(Candidate other) {
		Region a = clearRegionList();
		Region b = other.clearRegionList();
		Region t;

		if (a.resultStart < b.resultStart) {
			Region n = a.next;
			a.next = null;
			t = a;
			a = n;
		} else {
			Region n = b.next;
			b.next = null;
			t = b;
			b = n;
		}
		regionList = t;

		while (a != null && b != null) {
			if (a.resultStart < b.resultStart) {
				Region n = a.next;
				if (t.resultStart + t.length == a.resultStart)
					t.length += a.length;
				else {
					a.next = null;
					t.next = a;
					t = a;
				}
				a = n;
			} else {
				Region n = b.next;
				if (t.resultStart + t.length == b.resultStart)
					t.length += b.length;
				else {
					b.next = null;
					t.next = b;
					t = b;
				}
				b = n;
			}
		}

		if (a != null) {
			if (t.resultStart + t.length == a.resultStart) {
				t.length += a.length;
				a = a.next;
			}
			t.next = a;
			if (t.resultStart + t.length == b.resultStart) {
				t.length += b.length;
				b = b.next;
			}
			t.next = b;
		}
	}

