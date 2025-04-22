		StringBuilder builder = new StringBuilder();
		builder.append("Binding [triggerSequence=");//$NON-NLS-1$
		builder.append(getTriggerSequence());
		builder.append(", =command");//$NON-NLS-1$
		builder.append(command);
		builder.append(", schemeId=");//$NON-NLS-1$
		builder.append(schemeId);
		builder.append(", contextId=");//$NON-NLS-1$
		builder.append(contextId);
		builder.append(", locale=");//$NON-NLS-1$
		builder.append(locale);
		builder.append(", platform=");//$NON-NLS-1$
		builder.append(platform);
		builder.append(", type=");//$NON-NLS-1$
		builder.append((type == SYSTEM) ? "system" : "user");//$NON-NLS-1$ //$NON-NLS-2$
		builder.append("]");//$NON-NLS-1$
		string = builder.toString();
