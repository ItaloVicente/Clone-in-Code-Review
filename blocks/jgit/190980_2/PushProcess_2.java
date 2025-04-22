				Map<String
				toPush.clear();
				toPush.putAll(expanded);

				if (prePush != null) {
					try {
						prePush.setRefs(toPush.values());
						prePush.call();
					} catch (AbortedByHookException | IOException e) {
						throw new TransportException(e.getMessage()
					}
				}

				res.setRemoteUpdates(toPush);
