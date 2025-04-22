        FrameList frameList = navigator.getFrameList();
        goIntoAction = new GoIntoAction(frameList);
        backAction = new BackAction(frameList);
        forwardAction = new ForwardAction(frameList);
        upAction = new UpAction(frameList);
        goToResourceAction = new GotoResourceAction(navigator,
                ResourceNavigatorMessages.GoToResource_label);
    }
