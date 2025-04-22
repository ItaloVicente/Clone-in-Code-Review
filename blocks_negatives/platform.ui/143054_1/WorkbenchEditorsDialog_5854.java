                s.moveAbove(null);
                p.getWorkbenchWindow().setActivePage(p);
                p.activate(editor);
            } else {
                IWorkbenchPage p = window.getActivePage();
                if (p != null) {
                    try {
                        p.openEditor(input, desc.getId(), true);
                    } catch (PartInitException e) {
                    }
                }
            }
        }

        @Override
