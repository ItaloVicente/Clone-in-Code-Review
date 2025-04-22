			} else if (line.startsWith("shallow ")) {
				clientShallowCommits.add(ObjectId.fromString(line.substring(8)));
				depth = Integer.parseInt(line.substring(7));
				if (depth <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidDepth
									Integer.valueOf(depth)));
				}
