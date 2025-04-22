			while (!inf.finished()) {
				int uncompressed = inf.inflate(bytes
				p += uncompressed;
				if (uncompressed == 0 && !inf.finished()) {
					throw new CorruptObjectException(id
							"bad stream
				}
			}
