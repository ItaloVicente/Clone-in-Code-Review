			List otherAdapters = computeAdapterOrder(classesAndInterfaces);

			if (otherAdapters.isEmpty() && !adapters.isEmpty()) {
				removeNonCommonAdapters(adapters, classesAndInterfaces);
			} else {
				if (adapters.isEmpty()) {
					removeNonCommonAdapters(otherAdapters, lastCommonTypes);
					if (!otherAdapters.isEmpty()) {
