			if (last != null) {
				last.next = curr;
				last = curr;
			} else {
				first = curr;
				last = curr;
			}
			curr.next = null;
