			int index = -1;
			int nextIn = nextSpan(cardinality);
			int nextFlg = nextIn == distantCommitSpan
					? PackBitmapIndex.FLAG_REUSE : 0;

			for (RevCommit c : selectionHelper) {
				int distanceFromTip = cardinality - index - 1;
				if (distanceFromTip == 0) {
					break;
