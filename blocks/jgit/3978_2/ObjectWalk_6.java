			flags |= SEEN;
			o.flags = flags;
			if ((flags & UNINTERESTING) == 0 | boundary) {
				if (o instanceof RevTree) {
					tv = newTreeVisit(o);
					tv.parent = null;
					currVisit = tv;
				}
				return o;
