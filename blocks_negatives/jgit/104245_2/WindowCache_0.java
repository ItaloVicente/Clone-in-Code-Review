			if (r.canClear()) {
				clear(r);

				boolean found = false;
				final int s = slot(r.pack, r.position);
				final Entry e1 = table.get(s);
				for (Entry n = e1; n != null; n = n.next) {
					if (n.ref == r) {
						n.dead = true;
						found = true;
						break;
					}
				}
				if (found)
