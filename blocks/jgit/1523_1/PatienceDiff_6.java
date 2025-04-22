	private void splitIndexA(Edit index
			Edit iBefore
		splitHoldingQueue.clear();

		int bOut = index.beginA;
		int aOut = bOut + rBefore.getLengthA();
		final int seg1 = aOut;

		iBefore.beginA = bOut;
		iAfter.beginA = aOut;

		for (int p = index.beginA; p < seg1; p++) {
			long rec = aIndex[p];
			int line = elementOf(rec);

			if (line < rBefore.endA)
				aIndex[bOut++] = rec;

			else if (rAfter.beginA <= line) {
				long tmpRec = aIndex[aOut];
				int tmpLine = elementOf(tmpRec);
				if (tmpLine < rBefore.endA || rAfter.beginA <= tmpLine)
					splitHoldingQueue.add(tmpRec);
				aIndex[aOut++] = rec;
			}
		}

		for (int k = 0; k < splitHoldingQueue.size(); k++) {
			long rec = splitHoldingQueue.get(k);
			int line = elementOf(rec);

			if (line < rBefore.endA)
				aIndex[bOut++] = rec;

			else if (rAfter.beginA <= line) {
				long tmpRec = aIndex[aOut];
				int tmpLine = elementOf(tmpRec);
				if (tmpLine < rBefore.endA || rAfter.beginA <= tmpLine)
					splitHoldingQueue.add(tmpRec);
				aIndex[aOut++] = rec;
			}
		}

		for (int p = aOut; p < index.endA; p++) {
			long rec = aIndex[p];
			int line = elementOf(rec);

			if (line < rBefore.endA)
				aIndex[bOut++] = rec;

			else if (rAfter.beginA <= line)
				aIndex[aOut++] = rec;
		}

		iBefore.endA = bOut;
		iAfter.endA = aOut;
	}

	private void splitIndexB(Edit index
			Edit iBefore
		splitHoldingQueue.clear();

		int bOut = index.beginB;
		int aOut = bOut + rBefore.getLengthB();
		final int seg1 = aOut;

		iBefore.beginB = bOut;
		iAfter.beginB = aOut;

		for (int p = index.beginB; p < seg1; p++) {
			long rec = bIndex[p];
			int line = elementOf(rec);

			if (line < rBefore.endB)
				bIndex[bOut++] = rec;

			else if (rAfter.beginB <= line) {
				long tmpRec = bIndex[aOut];
				int tmpLine = elementOf(tmpRec);
				if (tmpLine < rBefore.endB || rAfter.beginB <= tmpLine)
					splitHoldingQueue.add(tmpRec);
				bIndex[aOut++] = rec;
			}
		}

		for (int k = 0; k < splitHoldingQueue.size(); k++) {
			long rec = splitHoldingQueue.get(k);
			int line = elementOf(rec);

			if (line < rBefore.endB)
				bIndex[bOut++] = rec;

			else if (rAfter.beginB <= line) {
				long tmpRec = bIndex[aOut];
				int tmpLine = elementOf(tmpRec);
				if (tmpLine < rBefore.endB || rAfter.beginB <= tmpLine)
					splitHoldingQueue.add(tmpRec);
				bIndex[aOut++] = rec;
			}
		}

		for (int p = aOut; p < index.endB; p++) {
			long rec = bIndex[p];
			int line = elementOf(rec);

			if (line < rBefore.endB)
				bIndex[bOut++] = rec;

			else if (rAfter.beginB <= line)
				bIndex[aOut++] = rec;
		}

		iBefore.endB = bOut;
		iAfter.endB = aOut;
	}

