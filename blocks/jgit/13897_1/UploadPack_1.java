					case TIP:
						if (tips == null)
							tips = refIdSet(db.getAllRefs().values());
						if (!tips.contains(obj))
							throw new PackProtocolException(MessageFormat.format(
									JGitText.get().wantNotValid
						break;
