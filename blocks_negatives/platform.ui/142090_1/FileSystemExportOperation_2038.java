            } else {
                IPath containersToCreate = currentResource.getFullPath()
                        .removeFirstSegments(
                                resource.getFullPath().segmentCount())
                        .removeLastSegments(1);

                for (int i = 0; i < containersToCreate.segmentCount(); i++) {
                    path = path.append(containersToCreate.segment(i));
                    exporter.createFolder(path);
                }
            }

            if (currentResource.getType() == IResource.FILE) {
