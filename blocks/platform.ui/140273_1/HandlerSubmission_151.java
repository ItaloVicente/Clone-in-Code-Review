		if (string == null) {
			final StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append("[activePartId="); //$NON-NLS-1$
			stringBuffer.append(activePartId);
			stringBuffer.append(",activeShell="); //$NON-NLS-1$
			stringBuffer.append(activeShell);
			stringBuffer.append(",activeWorkbenchSite="); //$NON-NLS-1$
			stringBuffer.append(activeWorkbenchPartSite);
			stringBuffer.append(",commandId="); //$NON-NLS-1$
			stringBuffer.append(commandId);
			stringBuffer.append(",handler="); //$NON-NLS-1$
			stringBuffer.append(handler);
			stringBuffer.append(",priority="); //$NON-NLS-1$
			stringBuffer.append(priority);
			stringBuffer.append(']');
			string = stringBuffer.toString();
		}
