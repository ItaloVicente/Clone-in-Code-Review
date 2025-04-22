		if (color != null && color.equals(titleColors.get(key))) {
			return;
		}
		if (color != null)
			titleColors.put(key, color);
		else
			titleColors.remove(key);
		updateHeaderImage = true;
