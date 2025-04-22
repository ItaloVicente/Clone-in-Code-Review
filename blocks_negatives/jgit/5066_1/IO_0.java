			int actSz = IO.readFully(in, buf, 0);

			if (actSz == sz) {
				byte[] ret = new byte[actSz];
				System.arraycopy(buf, 0, ret, 0, actSz);
				return ret;
			}
