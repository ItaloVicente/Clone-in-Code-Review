                    throws CoreException {
                SaveAsDialog dialog = new SaveAsDialog(clientFrame.getShell());
                IFile sFile = ResourceUtil.getFile(getEditorInput());
                if (sFile != null) {
                    dialog.setOriginalFile(sFile);
                    dialog.open();

                    IPath newPath = dialog.getResult();
                    if (newPath == null)
                        return;

                    if (dialog.getReturnCode() == Window.OK) {
                        String projectName = newPath.segment(0);
                        newPath = newPath.removeFirstSegments(1);
                        IProject project = resource.getWorkspace().getRoot()
                                .getProject(projectName);
                        newPath = project.getLocation().append(newPath);
                        File newFile = newPath.toFile();
                        if (saveFile(newFile)) {
                            IFile newResource = resource.getWorkspace().getRoot()
                                    .getFileForLocation(newPath);
                            if (newResource != null) {
                                sourceChanged(newResource);
                                newResource.refreshLocal(IResource.DEPTH_ZERO,
                                        monitor);
                            }
                        } else {
                            displayErrorDialog(SAVE_ERROR_TITLE, SAVE_ERROR_MESSAGE
                                    + newFile.getName());
                            return;
                        }
                    }
                }
            }
        };

    }

    @Override
