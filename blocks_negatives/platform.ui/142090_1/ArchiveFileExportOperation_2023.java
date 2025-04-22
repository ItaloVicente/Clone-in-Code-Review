        }

        resourcesToExport = resources;
        destinationFilename = filename;
    }

    /**
     *  Create an instance of this class.  Use this constructor if you wish
     *  to recursively export a single resource.
     *
     *  @param res org.eclipse.core.resources.IResource;
     *  @param filename java.lang.String
     */
    public ArchiveFileExportOperation(IResource res, String filename) {
        super();
        resource = res;
        destinationFilename = filename;
    }

    /**
     *  Create an instance of this class.  Use this constructor if you wish to
     *  export specific resources with a common parent resource (affects container
     *  directory creation)
     *
     *  @param res org.eclipse.core.resources.IResource
     *  @param resources java.util.Vector
     *  @param filename java.lang.String
     */
