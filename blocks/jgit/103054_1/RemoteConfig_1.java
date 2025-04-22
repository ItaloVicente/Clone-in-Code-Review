			Map<String
					KEY_PUSHINSTEADOF);
			if (!pushInsteadOf.isEmpty()) {
				for (String s : vlst) {
					String replaced = replaceUri(s
					if (!s.equals(replaced)) {
						pushURIs.add(new URIish(replaced));
					}
