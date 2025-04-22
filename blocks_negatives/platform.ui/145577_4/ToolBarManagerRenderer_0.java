				IContributionItem ici = modelToContribution.remove(itemModel);
				if (ici != null && parent != null) {
					parent.remove(ici);
				}
				if (ici != null) {
					ici.dispose();
