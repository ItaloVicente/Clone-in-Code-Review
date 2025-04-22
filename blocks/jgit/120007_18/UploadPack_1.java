				clientShallowCommits.add(ObjectId.fromString(line.substring(8)));
				depth = Integer.parseInt(line.substring(7));
				if (depth <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidDepth
									Integer.valueOf(depth)));
				}
				if (shallowSince != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				if (shallowExcludeRefs != null) {
					throw new PackProtocolException(
							JGitText.get().deepenNotWithDeepen);
				}
				List<String> exclude = shallowExcludeRefs;
				if (exclude == null) {
					exclude = shallowExcludeRefs = new ArrayList<>();
				}
				exclude.add(line.substring(11));
				if (depth != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenNotWithDeepen);
				}
			} else if (line.equals(OPTION_DEEPEN_RELATIVE)) {
				options.add(OPTION_DEEPEN_RELATIVE);
				shallowSince = Integer.parseInt(line.substring(13));
				if (shallowSince <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(
									JGitText.get().invalidTimestamp
				}
				if (depth !=  0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
