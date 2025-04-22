	private void processDynamicElements(final MenuManager menuManager,
			Menu menu, final MMenu menuModel) {

		final Map<MDynamicMenuContribution, ArrayList<MMenuElement>> toBeHidden = new HashMap<MDynamicMenuContribution, ArrayList<MMenuElement>>();

		MMenuElement[] ml = menuModel.getChildren().toArray(
				new MMenuElement[menuModel.getChildren().size()]);
		for (int i = 0; i < ml.length; i++) {

			MMenuElement currentMenuElement = ml[i];
			if (currentMenuElement instanceof MDynamicMenuContribution) {

				final Map<String, Object> storageMap = currentMenuElement
						.getTransientData();
				@SuppressWarnings("unchecked")
				ArrayList<MMenuElement> mel = (ArrayList<MMenuElement>) storageMap
						.get(MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);

				toBeHidden.put((MDynamicMenuContribution) currentMenuElement,
						mel);

				if (mel != null && mel.size() > 0) {

					for (MMenuElement item : mel) {
						item.setVisible(false);
					}

				}
				currentMenuElement.getTransientData().remove(
						MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);
			}
		}

