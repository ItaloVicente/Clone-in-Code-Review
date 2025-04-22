        if (!last) {
            return;
        }

        int initColon = findNextChar(responseContent, ':');
        responseContent.readerIndex(initColon);

        while (true) {
            int openBracketPos = findNextChar(responseContent, '{');
            int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
            if (closeBracketPos == -1) {
                break;
