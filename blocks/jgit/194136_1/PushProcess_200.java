				List<RemoteRefUpdate> willBeAttempted = preprocessed.values()
						.stream().filter(u -> {
							switch (u.getStatus()) {
							case NON_EXISTING:
							case REJECTED_NODELETE:
							case REJECTED_NONFASTFORWARD:
							case REJECTED_OTHER_REASON:
							case REJECTED_REMOTE_CHANGED:
							case UP_TO_DATE:
								return false;
							default:
								return true;
							}
						}).collect(Collectors.toList());
				if (!willBeAttempted.isEmpty()) {
					if (prePush != null) {
						try {
							prePush.setRefs(willBeAttempted);
							prePush.setDryRun(transport.isDryRun());
							prePush.call();
						} catch (AbortedByHookException | IOException e) {
							throw new TransportException(e.getMessage()
						}
					}
				}
