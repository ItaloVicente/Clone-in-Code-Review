		final Map<MDynamicMenuContribution, ArrayList<MMenuElement>> toBeHidden = new HashMap<MDynamicMenuContribution, ArrayList<MMenuElement>>();

		for (MMenuElement currentMenuElement : menuModel.getChildren()) {
			if (currentMenuElement instanceof MDynamicMenuContribution) {

				final Map<String, Object> storageMap = currentMenuElement.getTransientData();
				@SuppressWarnings("unchecked")
				ArrayList<MMenuElement> mel = (ArrayList<MMenuElement>) storageMap
						.get(MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);

				toBeHidden.put((MDynamicMenuContribution) currentMenuElement, mel);

				if (mel != null && mel.size() > 0) {

					for (MMenuElement item : mel) {
						item.setVisible(false);
					}
				}
				currentMenuElement.getTransientData().remove(MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);
			}
		}
