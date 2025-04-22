				});
				PushResultDialog.show(repository, result[0],
						op.getDestinationString(), false, false);
			}
			storeDialogState(repository.getBranch());
		} catch (IOException | URISyntaxException e) {
