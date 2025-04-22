			if (!isEmptyToolbar(ctrl)) {
				kids[count++] = kids[i];
			}
		}

		TrimLine curLine = new TrimLine();
		for (int i = 0; i <count; i++) {
			Control ctrl = kids[i];
