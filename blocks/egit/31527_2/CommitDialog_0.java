				boolean hasUnselected = false;
				for (Object element : selection.toList()) {
					if (!filesViewer.getChecked(element)) {
						hasUnselected = true;
						break;
					}
				}

				if (hasUnselected)
					manager.add(createSelectAction(selection));
