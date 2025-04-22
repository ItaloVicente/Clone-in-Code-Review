				final String shortName = entry.getKey();
				final String fullName = entry.getValue().getName();
				createMenuItem(menu, repository, currentBranch, fullName, shortName);
				localBranches.remove(shortName);
			}

			if (itemCount < MAX_NUM_MENU_ENTRIES && itemCount > 0 && sortedRefs.size() > 0) {
				sortedRefs.clear();
				sortedRefs.putAll(localBranches);
				new MenuItem(menu, SWT.SEPARATOR);
				for (final Entry<String, Ref> entry : sortedRefs.entrySet()) {
					itemCount++;
					if (itemCount > MAX_NUM_MENU_ENTRIES)
						break;
					final String fullName = entry.getValue().getName();
					final String shortName = entry.getKey();
					createMenuItem(menu, repository, fullName, shortName, shortName);
				}
