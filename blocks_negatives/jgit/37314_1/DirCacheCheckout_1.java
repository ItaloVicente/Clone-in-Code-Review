			for (int i = 0; i < bytes.length; i++) {
				if (bytes[i] == '/') {
					chk.checkPathSegment(bytes, segmentStart, i);
					segmentStart = i + 1;
				}
			}
			chk.checkPathSegment(bytes, segmentStart, bytes.length);
