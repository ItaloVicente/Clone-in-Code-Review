		} else {
			if (comparator.compare(base, remote))
				description = OUTGOING | DELETION;
			else
				description = CONFLICTING | CHANGE;
		}
