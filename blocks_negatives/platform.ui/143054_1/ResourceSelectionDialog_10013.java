                if (o instanceof IContainer) {
                    IResource[] members = null;
                    try {
                        members = ((IContainer) o).members();
                    } catch (CoreException e) {
                        return new Object[0];
                    }

                    ArrayList results = new ArrayList();
                    for (IResource member : members) {
                        if ((member.getType() & resourceType) > 0) {
                            results.add(member);
                        }
                    }
                    return results.toArray();
                }
                if (o instanceof ArrayList) {
                    return ((ArrayList) o).toArray();
                }
                return new Object[0];
            }
        };
    }

    /**
     * Initializes this dialog's controls.
     */
    private void initializeDialog() {
        selectionGroup.addCheckStateListener(event -> getOkButton().setEnabled(
		        selectionGroup.getCheckedElementCount() > 0));

        if (getInitialElementSelections().isEmpty()) {
