							if (text.length() > 0) {
								textList.add(text);

								Object replacedElement = elementMap.put(text, element);
								if (replacedElement != null && !replacedElement.equals(element)) {
									textList = (ArrayList) textMap.get(replacedElement);
									if (textList != null) {
										textList.remove(text);
										if (textList.isEmpty()) {
											textMap.remove(replacedElement);
											previousPicksList.remove(replacedElement);
										}
									}
