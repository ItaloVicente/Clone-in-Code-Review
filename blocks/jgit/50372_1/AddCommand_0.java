									AttributesNode entryAttributesNode = workingTreeIterator
											.getEntryAttributesNode();
									LocalFile attributeProcessedContent = null;
									if (entryAttributesNode != null) {
										attributeProcessedContent = handleAttributes(
												tw.getPathString()
												entryAttributesNode);
									}

