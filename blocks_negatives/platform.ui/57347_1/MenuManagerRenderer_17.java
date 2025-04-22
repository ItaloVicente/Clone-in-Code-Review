					MenuManager parent = getManager((MMenu) obj);
					if (itemModel.isToBeRendered()) {
						if (parent != null) {
							modelProcessSwitch(parent, itemModel);
						}
					} else {
						IContributionItem ici = getContribution(itemModel);
						clearModelToContribution(itemModel, ici);
						if (ici != null && parent != null) {
							parent.remove(ici);
						}
						if (ici != null) {
							ici.dispose();
						}
