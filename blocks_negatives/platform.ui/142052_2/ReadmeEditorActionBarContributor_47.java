        super.init(bars, page);
        bars.setGlobalActionHandler(IReadmeConstants.RETARGET2, handler2);
        bars.setGlobalActionHandler(IReadmeConstants.LABELRETARGET3, handler3);
        bars.setGlobalActionHandler(IReadmeConstants.ACTION_SET_RETARGET4,
                handler4);
        bars.setGlobalActionHandler(IReadmeConstants.ACTION_SET_LABELRETARGET5,
                handler5);

        page.addPartListener(action2);
        page.addPartListener(action3);
        IWorkbenchPart activePart = page.getActivePart();
        if (activePart != null) {
            action2.partActivated(activePart);
            action3.partActivated(activePart);
        }
    }

    @Override
