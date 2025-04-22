	boolean canMergeRegions(Candidate other) {
		return sourceCommit == other.sourceCommit
				&& sourcePath.getPath().equals(other.sourcePath.getPath());
	}

	void mergeRegions(Candidate other) {
		Region a = clearRegionList();
		Region b = other.clearRegionList();
		Region t = null;

		while (a != null && b != null) {
			if (a.resultStart < b.resultStart) {
				Region n = a.next;
				t = add(t
				a = n;
			} else {
				Region n = b.next;
				t = add(t
				b = n;
			}
		}

		if (a != null) {
			Region n = a.next;
			t = add(t
			t.next = n;
			Region n = b.next;
			t = add(t
			t.next = n;
		}
	}

