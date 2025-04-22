        IConfigurationElement[] elements = definingElement.getChildren(CHILD_ENABLEMENT);
        if (elements.length == 0) {
            String className = definingElement.getAttribute(ATT_OBJECT_CLASS);
            if (className != null) {
				enablement = new ActionExpression(ATT_OBJECT_CLASS,
                        className);
