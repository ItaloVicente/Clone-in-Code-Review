			String lineSeparator = System.lineSeparator();
			StringBuilder builder = new StringBuilder();
			builder.append("Command("); //$NON-NLS-1$
			builder.append(id);
			builder.append(',');
			builder.append(name == null ? "" : name); //$NON-NLS-1$
			builder.append(',');
			builder.append(lineSeparator);
			builder.append("\t\t"); //$NON-NLS-1$
			builder.append(description == null ? "" : description); //$NON-NLS-1$
			builder.append(',');
			builder.append(lineSeparator);
			builder.append("\t\t"); //$NON-NLS-1$
			builder.append(category == null ? "" : category.toString()); //$NON-NLS-1$
			builder.append(',');
			builder.append(lineSeparator);
			builder.append("\t\t"); //$NON-NLS-1$
			builder.append(handler == null ? "" : handler.toString()); //$NON-NLS-1$
			builder.append(',');
			builder.append(lineSeparator);
			builder.append("\t\t"); //$NON-NLS-1$
			builder.append(parameters == null ? "" : Arrays.toString(parameters)); //$NON-NLS-1$
			builder.append(',');
			builder.append(returnType == null ? "" : returnType.toString()); //$NON-NLS-1$
			builder.append(',');
			builder.append("" + defined); //$NON-NLS-1$
			builder.append(')');
			string = builder.toString();
