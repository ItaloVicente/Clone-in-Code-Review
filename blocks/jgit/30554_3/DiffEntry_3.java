			if (maxNumberOfEntries > 0 && r.size() > maxNumberOfEntries) {
				while (r.size() > maxNumberOfEntries)
					r.remove(r.size() - 1);
				break;
			}

