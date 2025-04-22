                if (o instanceof MinimizedFileSystemElement) {
                    MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
                    AdaptableList l = element.getFiles(structureProvider);
                    return l.getChildren(element);
                }
                return new Object[0];
            }
        };
    }

    /**
     *	Answer the root FileSystemElement that represents the contents of the
     *	currently-specified .zip file.  If this FileSystemElement is not
     *	currently defined then create and return it.
     */
    @Override
