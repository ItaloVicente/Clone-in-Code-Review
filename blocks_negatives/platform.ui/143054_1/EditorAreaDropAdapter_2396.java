        Display d = window.getShell().getDisplay();
        final IWorkbenchPage page = window.getActivePage();
        if (page != null) {
            d.asyncExec(() -> asyncDrop(event, page));
        }
    }
