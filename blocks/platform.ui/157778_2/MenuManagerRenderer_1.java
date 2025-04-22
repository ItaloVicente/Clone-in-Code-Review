			} else {
				IContributionItem ici = getContribution(itemModel);
				clearModelToContribution(itemModel, ici);
				if (ici != null && parent != null) {
					parent.remove(ici);
				}
				if (ici != null) {
					ici.dispose();
