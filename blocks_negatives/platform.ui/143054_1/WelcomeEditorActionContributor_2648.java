        IActionBars actionBars = getActionBars();
        if (actionBars != null) {
            actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                    ((WelcomeEditor) part).getCopyAction());
        }
    }
