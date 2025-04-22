        if (string == null) {
            final StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append(activePartId);
            stringBuffer.append(",activeShell="); //$NON-NLS-1$
            stringBuffer.append(activeShell);
            stringBuffer.append(",activeWorkbenchSite="); //$NON-NLS-1$
            stringBuffer.append(activeWorkbenchPartSite);
            stringBuffer.append(",contextId="); //$NON-NLS-1$
            stringBuffer.append(contextId);
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }
