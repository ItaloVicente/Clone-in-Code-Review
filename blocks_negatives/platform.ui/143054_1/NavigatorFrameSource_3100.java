        if (project != null && project.isOpen() == false) {
            MessageDialog
                    .openInformation(
                            navigator.getViewSite().getShell(),
                            ResourceNavigatorMessages.NavigatorFrameSource_closedProject_title,
                            NLS.bind(ResourceNavigatorMessages.NavigatorFrameSource_closedProject_message, project.getName()));
            navigator.getFrameList().back();
        } else {
            super.frameChanged(frame);
            navigator.updateTitle();
        }
    }
