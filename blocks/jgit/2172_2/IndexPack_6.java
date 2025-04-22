			if (last != null) {
				last.next = n;
				last = n;
			} else {
				first = n;
				last = n;
			}
			n.next = null;
