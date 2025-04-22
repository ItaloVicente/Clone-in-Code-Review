        private void updateSpans(final List<ThresholdLogSpan> spans, final ThresholdLogSpan span) {
            spans.add(span);

            Collections.sort(spans, Collections.<ThresholdLogSpan>reverseOrder());

            while(spans.size() > sampleSize) {
                spans.remove(spans.size() - 1);
