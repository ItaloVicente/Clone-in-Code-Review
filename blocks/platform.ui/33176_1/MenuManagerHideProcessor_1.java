					for (Entry<MDynamicMenuContribution, ArrayList<MMenuElement>> entry : toBeHidden
							.entrySet()) {
						MDynamicMenuContribution currentMenuElement = entry
								.getKey();
						Object contribution = ((MDynamicMenuContribution) currentMenuElement)
								.getObject();
