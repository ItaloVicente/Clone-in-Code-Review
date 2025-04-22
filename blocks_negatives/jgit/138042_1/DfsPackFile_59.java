				else if (n > remaining)
					n = (int) remaining;

				if (!packHeadSkipped) {
					out.write(buf.array(), 12, n - 12);
					packHeadSkipped = true;
				} else {
					out.write(buf.array(), 0, n);
