                if (action == IDialogConstants.CANCEL_ID) {
                    cancelSelected = true;
                    return IDialogConstants.CANCEL_ID;
                }
                if (action == IDialogConstants.YES_TO_ALL_ID) {
                    yesToAllSelected = true;
                    selectedChildren.add(resourceToCheck);
                }
            } else {
                boolean childResult = checkAcceptedResource(resourceToCheck,
                        selectedChildren);
                if (cancelSelected) {
