            if (createContainerDirectories
                    && resource.getType() != IResource.FILE) {
                path = path.append(resource.getName());
                exporter.createFolder(path);
            }
        }

        try {
            int totalWork = IProgressMonitor.UNKNOWN;
            try {
                if (resourcesToExport == null) {
