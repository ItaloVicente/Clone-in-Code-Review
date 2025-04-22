        boolean hScroll = hasStyle(control, SWT.H_SCROLL);
        boolean vScroll = hasStyle(control, SWT.V_SCROLL);

        boolean containsText = hasMethod(control, "setText", new Class<?>[] { String.class}); //$NON-NLS-1$

        boolean userEditable = !hasStyle(control, SWT.READ_ONLY) && containsText && hasMethod(control, "addModifyListener", new Class<?>[] { ModifyListener.class }); //$NON-NLS-1$

        if (userEditable) {
            if (hasStyle(control, SWT.MULTI)) {
                vScroll = true;
            }

            if (!wrapping) {
                hScroll = true;
            }
        }

        int hHint = SWT.DEFAULT;
        boolean grabHorizontal = hScroll;

        if (hScroll) {
            hHint = defaultSize.x;
        } else {

            if (wrapping) {
                if (containsText) {
                    hHint = wrapSize;
                    grabHorizontal = true;
                }
            }
        }

        int vAlign = SWT.FILL;

        if (!vScroll && !wrapping && !userEditable && containsText) {
            vAlign = SWT.CENTER;
        }

        return GridDataFactory.fillDefaults().grab(grabHorizontal, vScroll).align(SWT.FILL, vAlign).hint(hHint, vScroll ? defaultSize.y : SWT.DEFAULT);
    }

    private static boolean hasMethod(Control control, String name, Class<?>[] parameterTypes) {
        Class<? extends Control> c = control.getClass();
        try {
            c.getMethod(name, parameterTypes);
        } catch (SecurityException e) {
            return false;
        } catch (NoSuchMethodException e) {
            return false;
        }
