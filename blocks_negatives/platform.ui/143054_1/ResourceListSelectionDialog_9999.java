        } catch (CoreException e) {
        }
    }

    private Image getParentImage(IResource resource) {
        IResource parent = resource.getParent();
        return labelProvider.getImage(parent);
    }

    private String getParentLabel(IResource resource) {
        IResource parent = resource.getParent();
        String text;
        if (parent.getType() == IResource.ROOT) {
            text = labelProvider.getText(parent);
        } else {
            text = parent.getFullPath().makeRelative().toString();
        }
        if(text == null) {
