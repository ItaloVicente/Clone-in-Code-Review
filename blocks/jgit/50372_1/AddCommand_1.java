									long contentSize = (attributeProcessedContent == null) ? f
											.getEntryContentLength()
											: attributeProcessedContent
													.length();
									InputStream in = (attributeProcessedContent == null) ? f
											.openEntryStream()
											: attributeProcessedContent
											.openInputStream();
