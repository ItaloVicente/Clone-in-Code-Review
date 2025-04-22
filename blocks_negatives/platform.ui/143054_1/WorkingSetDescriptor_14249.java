        if (name == null) {
            throw new CoreException(new Status(IStatus.ERROR,
                    WorkbenchPlugin.PI_WORKBENCH, 0,
                    "Invalid extension (missing class name): " + id, //$NON-NLS-1$
                    null));
        }
