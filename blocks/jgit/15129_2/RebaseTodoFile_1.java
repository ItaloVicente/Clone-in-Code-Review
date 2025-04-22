				if (includeComments) {
					RebaseTodoLine line = null;
					String commentString = RawParseUtils.decode(buf
							tokenBegin
					try {
						skip = nextParsableToken(buf
						if (skip != -1) {
							line = parseLine(buf
							line.setAction(Action.COMMENT);
							line.setComment(commentString);
						}
					} catch (Exception e) {
						line = null;
					} finally {
						if (line == null)
							line = new RebaseTodoLine(commentString);
						r.add(line);
					}
				}
