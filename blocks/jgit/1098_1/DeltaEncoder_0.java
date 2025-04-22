		if (cnt == 0)
			return;

		int p = 0;

		while (MAX_V2_COPY < cnt) {
			p = encodeCopy(p
			offset += MAX_V2_COPY;
			cnt -= MAX_V2_COPY;

			if (buf.length < p + MAX_COPY_CMD_SIZE) {
				out.write(buf
				size += p;
				p = 0;
			}
