        int curPos = start;
        int matchStart = -1;
        int i;
        for (i = 0; i < segCount && curPos < end; ++i) {
            String current = fSegments[i];
            int nextMatch = regExpPosIn(text, curPos, end, current);
            if (nextMatch < 0) {
