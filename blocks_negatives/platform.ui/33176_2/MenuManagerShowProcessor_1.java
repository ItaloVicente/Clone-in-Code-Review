				Map<String, Object> storageMap = currentMenuElement
						.getTransientData();
				@SuppressWarnings("unchecked")
				ArrayList<MMenuElement> dump = (ArrayList<MMenuElement>) storageMap
						.get(DYNAMIC_ELEMENT_STORAGE_KEY);
				if (dump != null && dump.size() > 0)
					renderer.removeDynamicMenuContributions(menuManager,
							menuModel, dump);

				storageMap.remove(DYNAMIC_ELEMENT_STORAGE_KEY);

