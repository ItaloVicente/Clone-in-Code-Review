			if (!oldSeps.isEmpty()) {
				modelChildren.removeAll(oldSeps);
				for (MMenuSeparator model : oldSeps) {
					IContributionItem item = (IContributionItem) OpaqueElementUtil
							.getOpaqueItem(model);
					clearModelToContribution(model, item);
				}
