            if (dialog.open() == Window.OK
                    && pathVariablesGroup.performOk()) {
                setExtensionResult(selection, (IFileStore) dialog.getResult()[0]);
                super.okPressed();
            }
        } else {
