	@Override
	public Edit reduceCommonStartEnd(RawText a

		if (e.beginA == e.endA || e.beginB == e.endB)
			return e;

		byte[] aRaw = a.content;
		byte[] bRaw = b.content;

		int aPtr = a.lines.get(e.beginA + 1);
		int bPtr = a.lines.get(e.beginB + 1);

		int aEnd = a.lines.get(e.endA + 1);
		int bEnd = b.lines.get(e.endB + 1);

		if (aPtr < 0 || bPtr < 0 || aEnd > aRaw.length || bEnd > bRaw.length)
			throw new ArrayIndexOutOfBoundsException();

		while (aPtr < aEnd && bPtr < bEnd && aRaw[aPtr] == bRaw[bPtr]) {
			aPtr++;
			bPtr++;
		}

		while (aPtr < aEnd && bPtr < bEnd && aRaw[aEnd - 1] == bRaw[bEnd - 1]) {
			aEnd--;
			bEnd--;
		}

		e.beginA = findForwardLine(a.lines
		e.beginB = findForwardLine(b.lines

		e.endA = findReverseLine(a.lines
		e.endB = findReverseLine(b.lines

		return super.reduceCommonStartEnd(a
	}

	private static int findForwardLine(IntList lines
		final int end = lines.size() - 2;
		while (idx < end && lines.get(idx + 2) <= ptr)
			idx++;
		return idx;
	}

	private static int findReverseLine(IntList lines
		while (0 < idx && ptr <= lines.get(idx))
			idx--;
		return idx;
	}

