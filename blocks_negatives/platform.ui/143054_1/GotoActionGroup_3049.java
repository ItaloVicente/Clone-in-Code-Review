        actionBars.setGlobalActionHandler(IWorkbenchActionConstants.GO_INTO,
                goIntoAction);
        actionBars.setGlobalActionHandler(ActionFactory.BACK.getId(),
                backAction);
        actionBars.setGlobalActionHandler(ActionFactory.FORWARD.getId(),
                forwardAction);
        actionBars.setGlobalActionHandler(IWorkbenchActionConstants.UP,
                upAction);
        actionBars.setGlobalActionHandler(
                IWorkbenchActionConstants.GO_TO_RESOURCE, goToResourceAction);
