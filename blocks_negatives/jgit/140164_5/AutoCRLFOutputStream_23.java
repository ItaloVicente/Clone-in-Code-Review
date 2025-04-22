			if (c == '\r') {
				buf = '\r';
			} else if (c == '\n') {
				if (buf != '\r') {
					if (lastw < i) {
						out.write(b, lastw, i - lastw);
					}
					out.write('\r');
					lastw = i;
				}
				buf = -1;
			} else {
				buf = -1;
			}
