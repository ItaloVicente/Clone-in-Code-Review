            else if (createLinks) {
            	IFolder newFolder = workspace.getRoot().getFolder(resourcePath);
            	newFolder.createLink(
                        createRelativePath(new Path(provider.getFullPath(folderObject)), newFolder),
                        0, null);
                policy = POLICY_SKIP_CHILDREN;
            } else
                workspace.getRoot().getFolder(resourcePath).create(false, true, null);
        } catch (CoreException e) {
            errorTable.add(e.getStatus());
        }

        return policy;
    }

    /**
     * Transform an absolute path URI to a relative path one (i.e. from
     * "C:\foo\bar\file.txt" to "VAR\file.txt" granted that the relativeVariable
     * is "VAR" and points to "C:\foo\bar\").
     *
     * @param location
     * @param resource
     * @return an URI that was made relative to a variable
     */
    private IPath createRelativePath(IPath location, IResource resource) {
