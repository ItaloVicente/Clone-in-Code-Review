			if (hardWrapSegmentListener == null) {
				StyledText textWidget = getTextWidget();
				hardWrapSegmentListener = new BidiSegmentListener() {
					public void lineGetSegments(BidiSegmentEvent e) {
						int[] segments = calculateWrapOffsets(e.lineText, MAX_LINE_WIDTH);
						if (segments != null) {
							char[] segmentsChars = new char[segments.length];
							Arrays.fill(segmentsChars, '\n');
							e.segments = segments;
							e.segmentsChars = segmentsChars;
