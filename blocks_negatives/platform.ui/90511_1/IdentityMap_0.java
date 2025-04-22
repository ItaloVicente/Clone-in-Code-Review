				outer: for (Iterator<?> iterator = iterator(); iterator
						.hasNext();) {
					Object element = iterator.next();
					for (int i = 0; i < toRetain.length; i++)
						if (element == toRetain[i])
							continue outer;
					remove(element);
					changed = true;
				}
