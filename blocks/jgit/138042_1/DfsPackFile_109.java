
			int ptr = packHeadSkipped ? 0 : 12;
			buf.position(0);
			int bufLen = read(rc
			if (bufLen <= ptr) {
				throw packfileIsTruncated();
			}
			int n = (int) Math.min(bufLen - ptr
			out.write(buf.array()
			position += n;
			remaining -= n;
			packHeadSkipped = true;
