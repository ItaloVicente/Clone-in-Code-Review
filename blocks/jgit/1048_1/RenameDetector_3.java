	private void findContentRenames() throws IOException {

		if (added.isEmpty() || deleted.isEmpty())
			return;

		boolean[] paired = new boolean[deleted.size()];
		int[][] scores = calculateScores();

		class MaxLocation {
			int max = 0;
			int location;
		}

		MaxLocation[] rowMax = new MaxLocation[added.size()];
		MaxLocation[] colMax = new MaxLocation[deleted.size()];

		for (int r = 0; r < added.size(); r++) {
			MaxLocation rml = new MaxLocation();
			rowMax[r] = rml;
			for (int c = 0; c < deleted.size(); c++) {
				MaxLocation cml = colMax[c];
				if (cml == null) {
					cml = new MaxLocation();
					colMax[c] = cml;
				}
				if (scores[r][c] > rml.max) {
					rml.max = scores[r][c];
					rml.location = c;
				}
				if (scores[r][c] > colMax[c].max) {
					colMax[c].max = scores[r][c];
					colMax[c].location = r;
				}
			}
		}

		ArrayList<DiffEntry> tempAdded = new ArrayList<DiffEntry>(added.size());
		for (int r = 0; r < added.size(); r++) {
			int c = rowMax[r].location;
			if (colMax[c].location == r && scores[r][c] > 50) {
				entries.add(resolveRename(added.get(r)
						scores[r][c]));
				paired[c] = true;
			} else {
				tempAdded.add(added.get(r));
			}
		}
		added = tempAdded;

		ArrayList<DiffEntry> tempDeleted = new ArrayList<DiffEntry>(deleted
				.size());
		for (int i = 0; i < deleted.size(); i++)
			if (!paired[i])
				tempDeleted.add(deleted.get(i));

		deleted = tempDeleted;
	}

	private int[][] calculateScores() throws IOException {
		int[][] scores = new int[added.size()][deleted.size()];
		for (int r = 0; r < added.size(); r++) {
			for (int c = 0; c < deleted.size(); c++) {
				scores[r][c] = score(added.get(r).newId
			}
		}
		return scores;
	}

	private int score(AbbreviatedObjectId a
			throws IOException {
		byte[] ac = repo.openBlob(a.toObjectId()).getBytes();
		byte[] bc = repo.openBlob(b.toObjectId()).getBytes();

		HashCount[] aCounts = calcHashCounts(ac);
		HashCount[] bCounts = calcHashCounts(bc);


		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			HashCount aptr = aCounts[i];
			HashCount bptr = bCounts[i];

			while (aptr != null && bptr != null) {
				if (aptr.hash < bptr.hash) {
					aDiff += aptr.count;
					aptr = aptr.next;
				} else if (aptr.hash > bptr.hash) {
					bDiff += bptr.count;
					bptr = bptr.next;
				} else {
					if (aptr.count > bptr.count) {
						aDiff += aptr.count - bptr.count;
					} else {
						bDiff += bptr.count - aptr.count;
					}

					same += (aptr.count < bptr.count ? aptr.count : bptr.count);
					aptr = aptr.next;
					bptr = bptr.next;
				}
			}

			while (aptr != null) {
				aDiff += aptr.count;
				aptr = aptr.next;
			}

			while (bptr != null) {
				bDiff += bptr.count;
				bptr = bptr.next;
			}
		}

		return Math
				.round((same * 2) / ((float) same * 2 + aDiff + bDiff) * 100);
	}

	private HashCount[] calcHashCounts(byte[] bytes) {
		int s = 0;
		int e = 0;
		HashCount[] table = new HashCount[HASH_TABLE_SIZE];

		while (e < bytes.length) {
			if (bytes[e] == '\n' || e - s == 60 || e == bytes.length - 1) {
				int hash = hashLine(bytes
				int tableIndex = (hash >>> 1) % HASH_TABLE_SIZE;

				HashCount tmp = table[tableIndex];

				if (tmp == null) {
					HashCount hc = new HashCount(hash
					table[tableIndex] = hc;
				} else if (hash < tmp.hash) {
					HashCount hc = new HashCount(hash
					table[tableIndex] = hc;
				} else if (hash > tmp.hash) {
					while (tmp.hash != hash && tmp.next != null
							&& hash >= tmp.next.hash) {
						tmp = tmp.next;
					}
					if (tmp.hash == hash) {
						tmp.count++;
					} else if (tmp.next == null) {
						HashCount hc = new HashCount(hash
						tmp.next = hc;
					} else {
						HashCount hc = new HashCount(hash
						tmp.next = hc;
					}
				} else {
					tmp.count++;
				}

				s = e + 1;
			}

			e++;
		}

		return table;
	}

	int hashLine(final byte[] raw
		int hash = 5381;
		for (; ptr < end; ptr++)
			hash = (hash << 5) ^ (raw[ptr] & 0xff);
		return hash;
	}

