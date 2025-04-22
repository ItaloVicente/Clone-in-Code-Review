			if (pushMode){
				config.addPushURI(sp.getSelection().getURI());
				config.setPushRefSpecs(specPage.getRefSpecs());
			}
			else {
				config.addURI(sp.getSelection().getURI());
				config.setFetchRefSpecs(specPage.getRefSpecs());
				config.setTagOpt(specPage.getTagOpt());
			}
