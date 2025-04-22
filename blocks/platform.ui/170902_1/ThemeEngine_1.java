			if (idWithoutVersion != null) {
				List<String> styleSheetsWithoutVersion = stylesheets.get(idWithoutVersion);
				if (styleSheetsWithoutVersion != null) {
					s = new ArrayList<>(s);
					s.addAll(styleSheetsWithoutVersion);
				}
			}
