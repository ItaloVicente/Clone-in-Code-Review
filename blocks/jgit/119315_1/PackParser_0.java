			try (InputStream inf = inflate(Source.INPUT
				while (cnt < sz) {
					int r = inf.read(readBuffer);
					if (r <= 0)
						break;
					objectDigest.update(readBuffer
					checker.update(readBuffer
					cnt += r;
				}
