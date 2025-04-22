					previousPicksList.addFirst(element);

					ArrayList<String> textList = textMap.get(element);
					if (textList == null) {
						textList = new ArrayList<>();
						textMap.put(element, textList);
					}
