                }
                if (member.getType() == IResource.FOLDER) {
                    selectAppropriateFolderContents((IContainer) member);
                }
            }
        } catch (CoreException e) {
        }
    }

    /**
     * Records a resource's recursive descendents which are appropriate
     * for export based upon this page's current controls contents.
     *
     * @param resource the parent resource
     */
    protected void selectAppropriateResources(Object resource) {
        if (selectedResources == null) {

            if (exportSpecifiedTypesRadio.getSelection()) {
