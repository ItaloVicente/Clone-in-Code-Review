						if (!parts.isEmpty()) {
							ISaveHandler saveHandler = persp.getContext().get(ISaveHandler.class);
							if (parts.size() == 1) {
								Save responses = saveHandler.promptToSave(parts.get(0));
								switch (responses) {
								case CANCEL:
									return;
								case NO:
									break;
								case YES:
									partService.savePart(parts.get(0), false);
									break;
								}
							} else {
								Save[] responses = saveHandler.promptToSave(parts);
								for (Save response : responses) {
									if (response == Save.CANCEL) {
										return;
									}
								}

								for (int i = 0; i < responses.length; i++) {
									if (responses[i] == Save.YES) {
										partService.savePart(parts.get(i), false);
									}
								}
