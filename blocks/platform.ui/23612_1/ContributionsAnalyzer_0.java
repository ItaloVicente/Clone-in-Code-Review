
		if (id.equals("additions")) { //$NON-NLS-1$
			idx = 0;
			while (idx < size) {
				if ("PerspectiveSpacer".equals(menuModel.getChildren().get(idx).getElementId())) { //$NON-NLS-1$
					break;
				}
				idx++;
			}
			return idx;
		}

		return -1;
