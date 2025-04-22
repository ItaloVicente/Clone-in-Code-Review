		}
		if (pushURIs.isEmpty() && !pushInsteadOf.isEmpty()) {
			for (String s : vlst) {
				String replaced = replaceUri(s
				if (!s.equals(replaced)) {
					pushURIs.add(new URIish(replaced));
				}
			}
		}
