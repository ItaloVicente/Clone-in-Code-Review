				if (buf[ptr] == '\n') {
					bs[i++] = '\n';
					ptr++;
				} else {
					bs[i++] = '\r';
				}
			} else {
				byte b = buf[ptr];
				switch (b) {
				case '\r':
					hadCr = true;
					ptr++;
					bs[i++] = b;
					break;
				case '\n':
					if (!hadCr) {
						bs[i++] = '\r';
						if (i == end) {
							hadCr = true;
						} else {
							ptr++;
							bs[i++] = b;
						}
					} else {
						hadCr = false;
						ptr++;
						bs[i++] = b;
					}
					break;
				default:
					hadCr = false;
					ptr++;
					bs[i++] = b;
					break;
				}
			}
