				}
			});
			PushResultDialog.show(repository, result[0],
					op.getDestinationString(), false, false);
			storeLastUsedUri(uriCombo.getText());
			storeLastUsedBranch(branchText.getText());
			storeLastUsedTopic(topicText.isEnabled(),
					topicText.getText().trim(), repository.getBranch());
		} catch (URISyntaxException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (IOException e) {
