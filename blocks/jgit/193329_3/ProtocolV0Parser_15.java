				if (reqBuilder.getDeepenSince() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				if (reqBuilder.hasDeepenNots()) {
					throw new PackProtocolException(
							JGitText.get().deepenNotWithDeepen);
				}				reqBuilder.setDepth(depth);
				continue;
			}

				reqBuilder.addDeepenNot(line.substring(11));
				if (reqBuilder.getDepth() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenNotWithDeepen);
				}
				continue;
			}

				int ts = Integer.parseInt(line.substring(13));
				if (ts <= 0) {
					throw new PackProtocolException(MessageFormat
															.format(JGitText.get().invalidTimestamp
				}
				if (reqBuilder.getDepth() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				reqBuilder.setDeepenSince(ts);
