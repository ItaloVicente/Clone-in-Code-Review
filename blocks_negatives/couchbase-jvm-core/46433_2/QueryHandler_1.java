    /**
     * Finds the position of the correct closing character, taking into account the fact that before the correct one,
     * other sub section with same opening and closing characters can be encountered.
     *
     * @param buf the {@link ByteBuf} where to search for the end of a section enclosed in openingChar and closingChar.
     * @param openingChar the section opening char, used to detect a sub-section.
     * @param closingChar the section closing char, used to detect the end of a sub-section / this section.
     * @return
     */
    private static int findSectionClosingPosition(ByteBuf buf, char openingChar, char closingChar) {
        return buf.forEachByte(new ClosingPositionBufProcessor(openingChar, closingChar));
    }

