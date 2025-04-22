    private int findErrorBlockPosition(int openBracketPos) {
        int errorPosition = -1;

        int readerIndex = responseContent.readerIndex();
        for (int i = readerIndex; i < readerIndex + openBracketPos - 2; i++) {
            byte curr = responseContent.getByte(i);
            byte f1 = responseContent.getByte(i + 1);
            byte f2 = responseContent.getByte(i + 2);

            if (curr == '"' && f1 == 'e' && f2 == 'r') {
                errorPosition = i;
                break;
            }
        }
        return errorPosition > -1 ? errorPosition - responseContent.readerIndex() : errorPosition;
    }

