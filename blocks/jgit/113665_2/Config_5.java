				if (trailingSpaces == null) {
					trailingSpaces = new StringBuilder();
				}
				trailingSpaces.append(cc);
				continue;
			} else {
				inLeadingSpace = false;
				if (trailingSpaces != null) {
					value.append(trailingSpaces);
					trailingSpaces.setLength(0);
