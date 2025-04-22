			if (last != null)
				last.next = curr;
			else
				first = curr;
			last = curr;
			curr.next = null;
