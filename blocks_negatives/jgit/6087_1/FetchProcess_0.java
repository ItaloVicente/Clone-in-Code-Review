				try {
					monitor.update(1);
					u.update(walk);
					result.add(u);
				} catch (IOException err) {
					throw new TransportException(MessageFormat.format(JGitText
							.get().failureUpdatingTrackingRef,
							u.getLocalName(), err.getMessage()), err);
				}
