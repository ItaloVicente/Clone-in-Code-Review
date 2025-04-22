			if (region.getLength() == 0) {
				IRegion lineInfo = document.getLineInformationOfOffset(region
						.getOffset());
				int lineLength = lineInfo.getLength();
				int lineOffset = lineInfo.getOffset();
				int lineEnd = lineOffset + lineLength;
				int regionEnd = region.getOffset() + region.getLength();
				if (lineOffset < region.getOffset()) {
					int regionLength = Math.max(regionEnd, lineEnd)
							- lineOffset;
					contentOffset = lineOffset;
					content = document.get(lineOffset, regionLength);
					index = region.getOffset() - lineOffset;
				} else {
					int regionLength = Math.max(regionEnd, lineEnd)
							- region.getOffset();
					contentOffset = region.getOffset();
					content = document.get(contentOffset, regionLength);
					index = 0;
				}
			} else {
				content = document.get(region.getOffset(), region.getLength());
				contentOffset = region.getOffset();
				index = -1;
