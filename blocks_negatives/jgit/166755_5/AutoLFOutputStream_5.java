				if (buf == '\r') {
					out.write('\n');
					lastw = i + 1;
					buf = -1;
				} else {
					if (lastw < i + 1) {
						out.write(b, lastw, i + 1 - lastw);
					}
					lastw = i + 1;
