				if (adapters.isEmpty()) {
					removeNonCommonAdapters(otherAdapters, lastCommonTypes);
					if (!otherAdapters.isEmpty()) {
						adapters.addAll(otherAdapters);
					}
				} else {
					for (Iterator it = adapters.iterator(); it.hasNext();) {
						String adapter = (String) it.next();
						if (!otherAdapters.contains(adapter)) {
							it.remove();
						}
