        if (source instanceof byte[] && target instanceof IFile) {
            IFile file = (IFile) target;
            try {
                file.appendContents(new ByteArrayInputStream((byte[]) source),
                        false, true, null);
            } catch (CoreException e) {
                System.out
                        .println(MessageUtil
                return false;
            }
            return true;
        }
        return false;
    }
