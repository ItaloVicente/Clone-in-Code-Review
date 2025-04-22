					if (expand && pw instanceof Item) {
						Item item = (Item) pw;
						LinkedList toExpandList = new LinkedList();
						while (item != null && !getExpanded(item)) {
							toExpandList.addFirst(item);
							item = getParentItem(item);
						}
						for (Iterator it = toExpandList.iterator(); it
								.hasNext();) {
							Item toExpand = (Item) it.next();
							setExpanded(toExpand, true);
						}
					}
