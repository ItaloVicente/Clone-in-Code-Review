		Ref r;
		while ((r = (Ref) queue.poll()) != null) {
			clear(r);

			final int s = slot(r.pack, r.position);
			final Entry e1 = table.get(s);
			for (Entry n = e1; n != null; n = n.next) {
				if (n.ref == r) {
					n.dead = true;
					table.compareAndSet(s, e1, clean(e1));
					break;
				}
			}
		}
