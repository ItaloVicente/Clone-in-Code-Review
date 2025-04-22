						buffer.append(ESCAPED_QUOTE
								+ Util.replaceAll(be.getCategory(),
										ESCAPED_QUOTE, REPLACEMENT)
								+ ESCAPED_QUOTE + DELIMITER);
						buffer.append(ESCAPED_QUOTE + be.getName()
								+ ESCAPED_QUOTE + DELIMITER);
						buffer.append(ESCAPED_QUOTE + be.getTrigger().format()
								+ ESCAPED_QUOTE + DELIMITER);
						buffer.append(ESCAPED_QUOTE + be.getContext().getName()
