			buf.position(0);
			int n = read(rc
			if (n <= 0)
				throw packfileIsTruncated();
			else if (n > remaining)
				n = (int) remaining;

			if (!packHeadSkipped) {
				out.write(buf.array()
				packHeadSkipped = true;
			} else {
				out.write(buf.array()
