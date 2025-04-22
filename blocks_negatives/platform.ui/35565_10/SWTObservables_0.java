		synchronized (realms) {
			for (DisplayRealm element : realms) {
				if (element.display == display) {
					return element;
				}
			}
			DisplayRealm result = new DisplayRealm(display);
			realms.add(result);
			return result;
		}
