			if (descendingFields.contains(fields[i])) {
				value = fields[i].compare(item1, item0);
			} else {
				value = fields[i].compare(item0, item1);
			}
			if (value != 0) {
