    private int findErrorBlockPosition() {
        int errorPosition = -1;
        for (int i = responseContent.readerIndex(); i < responseContent.writerIndex() - 2; i++) {
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

