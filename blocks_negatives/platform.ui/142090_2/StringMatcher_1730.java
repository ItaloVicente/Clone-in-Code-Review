        int pos = 0;
        StringBuilder buf = new StringBuilder();
        while (pos < fLength) {
            char c = fPattern.charAt(pos++);
            switch (c) {
            case '\\':
                if (pos >= fLength) {
                    buf.append(c);
                } else {
                    char next = fPattern.charAt(pos++);
                    /* if it's an escape sequence */
                    if (next == '*' || next == '?' || next == '\\') {
                        buf.append(next);
                    } else {
                        /* not an escape sequence, just insert literally */
                        buf.append(c);
                        buf.append(next);
                    }
                }
                break;
            case '*':
                if (buf.length() > 0) {
                    /* new segment */
