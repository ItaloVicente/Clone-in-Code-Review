				} else {
					IContributionItem ici = getContribution(itemModel1);
					clearModelToContribution(itemModel1, ici);
					if (ici != null && parent != null) {
						parent.remove(ici);
					}
					if (ici != null) {
						ici.dispose();
