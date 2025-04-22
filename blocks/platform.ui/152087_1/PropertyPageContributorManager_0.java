			if (isMultiSelection) {
				it = collection.iterator();
				while (it.hasNext()) {
					RegistryPageContributor contrib = (RegistryPageContributor) it.next();
					if (!contrib.supportsMultipleSelection()) {
						it.remove();
					}
				}
			}
