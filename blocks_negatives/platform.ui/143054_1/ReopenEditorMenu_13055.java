                    String title = WorkbenchMessages.OpenRecent_errorTitle;
                    String msg = NLS.bind(WorkbenchMessages.OpenRecent_unableToOpen,  itemName );
                    MessageDialog.openWarning(window.getShell(), title, msg);
                    history.remove(item);
                } else {
                    page.openEditor(input, desc.getId());
                }
            } catch (PartInitException e2) {
                String title = WorkbenchMessages.OpenRecent_errorTitle;
                MessageDialog.openWarning(window.getShell(), title, e2
                        .getMessage());
                history.remove(item);
            }
        }
    }
