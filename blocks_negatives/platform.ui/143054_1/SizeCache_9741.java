            return true;
        }

        if (control instanceof Button || control instanceof ProgressBar
                || control instanceof Sash || control instanceof Scale
                || control instanceof Slider || control instanceof List
                || control instanceof Combo || control instanceof Tree) {
            return true;
        }

        if (control instanceof Label || control instanceof Text) {
            return (control.getStyle() & SWT.WRAP) == 0;
        }


        return false;
    }

    /**
     * Try to figure out how much we need to subtract from the hints that we
     * pass into the given control's computeSize(...) method. This tries to
     * compensate for bug 46112. To be removed once SWT provides an "official"
     * way to compute one dimension of a control's size given the other known
     * dimension.
     *
     * @param control
     */
    private void computeHintOffset(Control control) {
