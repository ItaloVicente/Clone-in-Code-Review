            IResource resource = getSourceResource();
            resourceDetailsButton.setEnabled(resource != null
                    && resource.isAccessible());
        }

        exportSpecifiedTypesRadio.setEnabled(!exportCurrentSelection);
        typesToExportField.setEnabled(exportSpecifiedTypesRadio.getSelection());
        typesToExportEditButton.setEnabled(exportSpecifiedTypesRadio
                .getSelection());
    }

    @Override
