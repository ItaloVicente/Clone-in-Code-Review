			for (Iterator iterator = manager.getElements(
					PreferenceManager.PRE_ORDER).iterator(); iterator.hasNext();) {
				IPreferenceNode node = (IPreferenceNode) iterator.next();
				if (node.getId().equals(id)) {
					dialog.showPage(node);
					break;
				}
			}
