        AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.DEL);
        AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.DEL);
        AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.CTRL);
        AutomationUtil.performCharacterEvent(display, SWT.KeyDown,'S');
        AutomationUtil.performCharacterEvent(display, SWT.KeyUp,'S');
        AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.CTRL);
