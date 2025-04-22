			}
		} else {
			if (entry.isSmudged()) {
				return contentCheck(entry);
			} else {
				return false;
			}
