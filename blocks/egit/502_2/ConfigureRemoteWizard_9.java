			}
			if (srp.configurePush.getSelection()) {
				RepositorySelectionPage sp = (RepositorySelectionPage) getPages()[3];
				config.addPushURI(sp.getSelection().getURI());
				RefSpecPage specPage = (RefSpecPage) getPages()[4];
				config.setPushRefSpecs(specPage.getRefSpecs());
				config.update(myConfiguration);
				sp.saveUriInPrefs(sp.getSelection().getURI().toString());
			}
