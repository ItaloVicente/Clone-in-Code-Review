
		if (id.equals("additions")) { //$NON-NLS-1$
			idx = menuModel.getChildren().size() - 1;
			while (idx > -1) {
				if ("PerspectiveSpacer".equals(menuModel.getChildren().get(idx).getElementId())) { //$NON-NLS-1$
					break;
				}
				idx--;
			}
		}

		return idx;
