    
    public void remove(String symbolicName) {
        Assert.isNotNull(symbolicName);

        RGB existing = stringToRGB.remove(symbolicName);
        if (existing == null) {
			return;
		}

        fireMappingChanged(symbolicName, existing, null);
        Color oldColor = stringToColor.remove(symbolicName);
        if (oldColor != null) {
			staleColors.add(oldColor);
		}
    }
