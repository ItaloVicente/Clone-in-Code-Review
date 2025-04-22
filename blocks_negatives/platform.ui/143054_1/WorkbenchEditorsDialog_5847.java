        } else {
            IWorkbenchPage page = window.getActivePage();
            if (page != null) {
                updateEditors(new IWorkbenchPage[] { page });
            }
        }
