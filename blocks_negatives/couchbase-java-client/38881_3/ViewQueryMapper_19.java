    static class MarkersProcessor implements ByteBufProcessor {
        List<Integer> markers = new ArrayList<Integer>();
        private int depth;
        private byte open = '{';
        private byte close = '}';
        private int counter;
        @Override
        public boolean process(byte value) throws Exception {
            counter++;
            if (value == open) {
                depth++;
            }
            if (value == close) {
                depth--;
                if (depth == 0) {
                    markers.add(counter);
                }
            }
            return true;
        }
