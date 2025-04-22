					case SQUASH:
						squash = true;
					case FIXUP:
						RevWalk rw = new RevWalk(repo);
						RevCommit headCommit = rw.parseCommit(repo
								.resolve(Constants.HEAD));
						rw.release();
						try {
							Pattern pattern = Pattern
							Matcher matcher = pattern
									.matcher(squashCommitMessage);
							squashCount = Integer.parseInt(matcher.group(0));
						} catch (Throwable t) {
						} finally {
							squashCount++;
						}

						if (squashCount == 1) {
							squashMessageBuilder.setLength(0);
							squashMessageBuilder
							squashMessageBuilder

							String headCommitMessage = headCommit
									.getFullMessage();
							squashMessageBuilder.append(headCommitMessage);

						} else {
							squashMessageBuilder.delete(0
									squashMessageBuilder.indexOf("\n"));
							squashMessageBuilder.insert(0
									"# This is a combination of " + squashCount
											+ "commits.\n");
						}

						if (squash) {
							squashMessageBuilder
							squashMessageBuilder.append(commitToPick
									.getFullMessage());

						} else {
							squashMessageBuilder
							squashMessageBuilder
									.append(commitToPick.getFullMessage()
											.replaceAll("\n"
						}

						newHead = new Git(repo).commit()
								.setAuthor(headCommit.getAuthorIdent())
								.setAmend(true)
								.setMessage(squashMessageBuilder.toString())
								.setReflogComment(step.action.token).call();

						continue;
