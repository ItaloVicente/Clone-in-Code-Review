			if (ch == '\r') {
				if (i + 1 < buf.length && in.charAt(i + 1) == '\n') {
					buf[o++] = ' ';
					++i;
				} else
					buf[o++] = ' ';
			} else if (ch == '\n')
				buf[o++] = ' ';
			else
				buf[o++] = ch;
