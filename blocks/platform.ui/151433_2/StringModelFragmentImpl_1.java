			targetElements = new ArrayList<>();
			try {
				while (i.hasNext()) {
					Object obj = i.next();
					if (obj instanceof MApplicationElement) {
						MApplicationElement o = (MApplicationElement) obj;
						targetElements.add(o);
					}
