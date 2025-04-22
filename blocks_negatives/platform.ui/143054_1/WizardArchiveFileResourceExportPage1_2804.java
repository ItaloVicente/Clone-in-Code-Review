        FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.SAVE | SWT.SHEET);
        dialog.setFilterExtensions(new String[] { "*.zip;*.tar.gz;*.tar;*.tgz", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$
        dialog.setText(DataTransferMessages.ArchiveExport_selectDestinationTitle);
        String currentSourceString = getDestinationValue();
        int lastSeparatorIndex = currentSourceString
                .lastIndexOf(File.separator);
        if (lastSeparatorIndex != -1) {
