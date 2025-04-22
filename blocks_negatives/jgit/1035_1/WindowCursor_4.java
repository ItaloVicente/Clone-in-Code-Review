		for (;;) {
			pin(pack, position);
			dstoff = window.inflate(position, dstbuf, dstoff, inf);
			if (inf.finished())
				return dstoff;
			position = window.end;
		}
