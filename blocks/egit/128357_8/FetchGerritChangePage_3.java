			} catch (URISyntaxException e) {
				Activator.handleError(e.getMessage(), e, false);
				setErrorMessage(e.getMessage());
			}
			for (String aUri : uris) {
				uriCombo.add(aUri);
				uriCombo.setData(aUri, repo);
				changeRefs.put(aUri, new ChangeList(repo, aUri));
				if (repo == repository) {
					repositoryMatched = true;
					int length = uriCombo.getItemCount() - 1;
					uriCombo.select(length);
				}
