			int actSz = IO.readFully(in

			if (actSz == sz) {
				byte[] ret = new byte[actSz];
				System.arraycopy(buf
				return ret;
			}
