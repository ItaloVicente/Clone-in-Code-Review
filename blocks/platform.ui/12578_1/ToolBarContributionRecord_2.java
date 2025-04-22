		if (currentVisibility
				&& item.getPersistedState().get(
						MenuManagerRenderer.VISIBILITY_IDENTIFIER) != null) {
			String identifier = item.getPersistedState().get(
					MenuManagerRenderer.VISIBILITY_IDENTIFIER);
			Object rc = exprContext.eclipseContext.get(identifier);
			if (rc instanceof Boolean) {
				currentVisibility = ((Boolean) rc).booleanValue();
			}
		}
