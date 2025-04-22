			if (c == '\r') {
				if (lastw < i) {
					out.write(b, lastw, i - lastw);
				}
				lastw = i + 1;
				buf = '\r';
			} else if (c == '\n') {
				if (buf == '\r') {
					out.write('\n');
					lastw = i + 1;
					buf = -1;
				} else {
					if (lastw < i + 1) {
						out.write(b, lastw, i + 1 - lastw);
					}
					lastw = i + 1;
				}
			} else {
				if (buf == '\r') {
					out.write('\r');
					lastw = i;
				}
				buf = -1;
			}
