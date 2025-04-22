				for (Iterator j = getElements(PreferenceManager.POST_ORDER)
						.iterator(); j.hasNext();) {
					IPreferenceNode element = (IPreferenceNode) j
							.next();
					if (category.equals(element.getId())) {
						parent = element;
						break;
					}
				}
