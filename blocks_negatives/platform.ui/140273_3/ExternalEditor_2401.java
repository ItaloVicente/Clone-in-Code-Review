        } catch (Exception e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            WorkbenchPlugin.PI_WORKBENCH,
                            0,
                            NLS.bind(WorkbenchMessages.ExternalEditor_errorMessage,programFileName),
                            e));
        }
    }
