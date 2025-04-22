        Iterator sourceIter = sources.iterator();
        IPath sourceRootPath = null;

        if (this.source != null) {
            sourceRootPath = new Path(provider.getFullPath(this.source));
        }
        while (sourceIter.hasNext()) {
            Object nextSource = sourceIter.next();
            IPath sourcePath = new Path(provider.getFullPath(nextSource));
            IPath newDestinationPath;
            IResource newDestination;

            if (sourceRootPath == null) {
                newDestinationPath = sourceStart.append(provider
                        .getLabel(nextSource));
            } else {
                int prefixLength = sourcePath
                        .matchingFirstSegments(sourceRootPath);
                IPath relativeSourcePath = sourcePath
                        .removeFirstSegments(prefixLength);
                newDestinationPath = this.destinationPath
                        .append(relativeSourcePath);
            }
            newDestination = workspaceRoot.findMember(newDestinationPath);
            if (newDestination == null) {
