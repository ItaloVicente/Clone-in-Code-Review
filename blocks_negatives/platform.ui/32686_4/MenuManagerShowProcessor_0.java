				Object contribution = ((MDynamicMenuContribution) currentMenuElement)
						.getObject();
				if (contribution == null) {
					IEclipseContext context = modelService
							.getContainingContext(menuModel);
					contribution = contributionFactory.create(
							((MDynamicMenuContribution) currentMenuElement)
									.getContributionURI(), context);
					((MDynamicMenuContribution) currentMenuElement)
							.setObject(contribution);
				}
