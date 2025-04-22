				EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return element.getContributorURI() != null
								&& element.getContributorURI().equals(contributorURI);
					}
				});
