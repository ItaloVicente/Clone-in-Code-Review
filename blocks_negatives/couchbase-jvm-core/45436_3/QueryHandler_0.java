        ClosingPositionBufProcessor processor = new ClosingPositionBufProcessor(openingChar, closingChar);
        return buf.forEachByte(processor);
    }

    private static class ClosingPositionBufProcessor implements ByteBufProcessor {
        private int openCount = 0;
        private final char openingChar;
        private final char closingChar;

        public ClosingPositionBufProcessor(char openingChar, char closingChar) {
            this.openingChar = openingChar;
            this.closingChar = closingChar;
        }

        @Override
        public boolean process(byte current) throws Exception {
            if (current == openingChar) {
                openCount++;
            } else if (current == closingChar && openCount > 0) {
                openCount--;
                if (openCount == 0) {
                    return false;
                }
            }
            return true;
        }
