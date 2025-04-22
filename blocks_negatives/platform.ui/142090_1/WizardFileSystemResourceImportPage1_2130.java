                    IProgressMonitor monitor) throws InterruptedException {
                if (files == null) {
                    throw new InterruptedException();
                }
                Iterator filesList = files.iterator();
                while (filesList.hasNext()) {
                    if (monitor.isCanceled()) {
