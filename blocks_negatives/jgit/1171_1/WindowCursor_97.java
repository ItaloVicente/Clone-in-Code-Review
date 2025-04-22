		for (;;) {
			pin(pack, position);
			window.inflateVerify(position, inf);
			if (inf.finished())
				return;
			position = window.end;
		}
