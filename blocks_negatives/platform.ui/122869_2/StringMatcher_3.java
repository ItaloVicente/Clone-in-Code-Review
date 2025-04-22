            return new Position(x, x + fLength);
        }

        int segCount = fSegments.length;
        if (segCount == 0) {
			return new Position(start, end);
		}

        int curPos = start;
        int matchStart = -1;
        int i;
        for (i = 0; i < segCount && curPos < end; ++i) {
            String current = fSegments[i];
            int nextMatch = regExpPosIn(text, curPos, end, current);
            if (nextMatch < 0) {
				return null;
			}
            if (i == 0) {
				matchStart = nextMatch;
