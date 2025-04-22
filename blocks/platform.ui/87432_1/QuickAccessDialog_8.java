							}
							previousPicksList.addFirst(element);

							ArrayList textList = (ArrayList) textMap.get(element);
							if (textList == null) {
								textList = new ArrayList();
								textMap.put(element, textList);
							}
