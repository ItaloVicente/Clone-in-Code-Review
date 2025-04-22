			if (itemCount < MAX_NUM_MENU_ENTRIES) {
				if (itemCount > 0 && localBranches.size() > 0)
					createSeparator(menu);

				sortedRefs.clear();
				sortedRefs.putAll(localBranches);
				for (final Entry<String, Ref> entry : sortedRefs.entrySet()) {
					itemCount++;
					if (itemCount > MAX_NUM_MENU_ENTRIES)
						break;
					final String fullName = entry.getValue().getName();
					final String shortName = entry.getKey();
					createMenuItem(menu, repository, currentBranch, fullName, shortName);
				}
