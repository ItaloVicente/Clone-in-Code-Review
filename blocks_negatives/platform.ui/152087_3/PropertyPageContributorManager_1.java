		}
		if (result != null && !result.isEmpty() && selection.size() > 1) {
			it = result.iterator();
			while (it.hasNext()) {
				RegistryPageContributor contrib = (RegistryPageContributor) it.next();
				if (!contrib.supportsMultipleSelection())
					it.remove();
