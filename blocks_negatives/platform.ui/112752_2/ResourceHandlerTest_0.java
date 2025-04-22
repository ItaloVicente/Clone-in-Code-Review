			if (check.equals(application.getHandlers().get(0)
					.getContributionURI())) {
				application.getHandlers().remove(0);
			} else if (check.equals(application.getHandlers().get(1)
					.getContributionURI())) {
				application.getHandlers().remove(1);
