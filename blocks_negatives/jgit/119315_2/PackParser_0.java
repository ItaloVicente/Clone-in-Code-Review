			while (cnt < sz) {
				int r = inf.read(readBuffer);
				if (r <= 0)
					break;
				objectDigest.update(readBuffer, 0, r);
				checker.update(readBuffer, 0, r);
				cnt += r;
