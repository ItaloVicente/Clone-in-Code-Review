				if (contribution.actions != null) {
					for (Object obj : contribution.actions) {
						ActionDescriptor ad = (ActionDescriptor) obj;
						System.out.println(ad.getAction().getText());
					}
					if (contribution.isAdjunctContributor()) {
						adjunctContributions.add(contribution);
					}
				}
