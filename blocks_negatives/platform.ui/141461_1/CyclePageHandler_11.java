			item.putData(K_PAGE, viewPage);
			String name = pageSwitcher.getName(viewPage);
			if (name.length() > TEXT_LIMIT) {
				name = name.substring(0, TEXT_LIMIT) + "..."; //$NON-NLS-1$
			}
			item.setText(name);
			rows.add(item);
