		AndExpression andExpression = new AndExpression();
		String identifierId = createIdentifierId(item);
		if (identifierId != null) {
			IIdentifier identifier = activityManager.getIdentifier(identifierId);
			andExpression.add(new EnabledIdentifierExpression(identifier));
		}

		if (visibleWhen != null) {
			andExpression.add(visibleWhen);
		} else {
			andExpression.add(AlwaysEnabledExpression.INSTANCE);
		}

		itemsToExpressions.put(item, andExpression);
