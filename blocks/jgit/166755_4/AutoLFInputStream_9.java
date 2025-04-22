			if (isBinary) {
				int toCopy = Math.min(cnt - ptr
				System.arraycopy(buf
				i += toCopy;
				ptr += toCopy;
			} else if (!isCrLf) {
				byte b = buf[ptr++];
				if (b != '\r') {
					bs[i++] = b;
					continue;
				}
