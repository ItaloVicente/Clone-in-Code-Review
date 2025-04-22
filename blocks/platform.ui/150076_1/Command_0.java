			String lineSeparator = System.lineSeparator();
			StringBuilder buffer = new StringBuilder();
			buffer.append("Command("); //$NON-NLS-1$
			buffer.append(id);
			buffer.append(',');
			buffer.append(name == null ? "" : name); //$NON-NLS-1$
			buffer.append(',');
			buffer.append(lineSeparator);
			buffer.append("\t\t"); //$NON-NLS-1$
			buffer.append(description == null ? "" : description); //$NON-NLS-1$
			buffer.append(',');
			buffer.append(lineSeparator);
			buffer.append("\t\t"); //$NON-NLS-1$
			buffer.append(category == null ? "" : category.toString()); //$NON-NLS-1$
			buffer.append(',');
			buffer.append(lineSeparator);
			buffer.append("\t\t"); //$NON-NLS-1$
			buffer.append(handler == null ? "" : handler.toString()); //$NON-NLS-1$
			buffer.append(',');
			buffer.append(lineSeparator);
			buffer.append("\t\t"); //$NON-NLS-1$
			buffer.append(parameters == null ? "" : Arrays.toString(parameters)); //$NON-NLS-1$
			buffer.append(',');
			buffer.append(returnType == null ? "" : returnType.toString()); //$NON-NLS-1$
			buffer.append(',');
			buffer.append("" + defined); //$NON-NLS-1$
			buffer.append(')');
			string = buffer.toString();
