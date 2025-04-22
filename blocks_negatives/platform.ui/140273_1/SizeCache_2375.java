    private Control control;

    private Point preferredSize;

    private Point cachedWidth;

    private Point cachedHeight;

    /**
     * True iff we should recursively flush all children on the next layout
     */
    private boolean flushChildren;

    /**
     * True iff changing the height hint does not affect the preferred width and changing
     * the width hint does not change the preferred height
     */
    private boolean independentDimensions = false;

    /**
     * True iff the preferred height for any hint larger than the preferred width will not
     * change the preferred height.
     */
    private boolean preferredWidthOrLargerIsMinimumHeight = false;

    private int widthAdjustment = 0;

    private int heightAdjustment = 0;


    public SizeCache() {
        this(null);
    }

    /**
     * Creates a cache for size computations on the given control
     *
     * @param control the control for which sizes will be calculated,
     * or null to always return (0,0)
     */
    public SizeCache(Control control) {
        setControl(control);
    }

    /**
     * Sets the control whose size is being cached. Does nothing (will not
     * even flush the cache) if this is the same control as last time or
     * it is already disposed.
     *
     * @param newControl the control whose size is being cached, or null to always return (0,0)
     */
    public void setControl(Control newControl) {
        if (newControl != control && !newControl.isDisposed()) {
            control = newControl;
            if (control == null) {
                independentDimensions = true;
                preferredWidthOrLargerIsMinimumHeight = false;
                widthAdjustment = 0;
                heightAdjustment = 0;
            } else {
                independentDimensions = independentLengthAndWidth(control);
                preferredWidthOrLargerIsMinimumHeight = isPreferredWidthMaximum(control);
                computeHintOffset(control);
                flush();
            }
        }
    }

    /**
     * Returns the control whose size is being cached
     *
     * @return the control whose size is being cached, or null if this cache always returns (0,0)
     */
    public Control getControl() {
        return control;
    }

    /**
     * Flush the cache (should be called if the control's contents may have changed since the
     * last query)
     */
    public void flush() {
        flush(true);
    }

    public void flush(boolean recursive) {
        preferredSize = null;
        cachedWidth = null;
        cachedHeight = null;
        this.flushChildren = recursive;
    }

    private Point getPreferredSize() {
        if (preferredSize == null) {
            preferredSize = computeSize(control, SWT.DEFAULT, SWT.DEFAULT);
        }

        return preferredSize;
    }

    /**
     * Computes the preferred size of the control.
     *
     * @param widthHint the known width of the control (pixels) or SWT.DEFAULT if unknown
     * @param heightHint the known height of the control (pixels) or SWT.DEFAULT if unknown
     * @return the preferred size of the control
     */
    public Point computeSize(int widthHint, int heightHint) {
        if (control == null) {
            return new Point(0, 0);
        }

        if (widthHint != SWT.DEFAULT && heightHint != SWT.DEFAULT) {
            return new Point(widthHint, heightHint);
        }

        if (widthHint == SWT.DEFAULT && heightHint == SWT.DEFAULT) {
            return Geometry.copy(getPreferredSize());
        }

        if (independentDimensions) {
            Point result = Geometry.copy(getPreferredSize());

            if (widthHint != SWT.DEFAULT) {
                result.x = widthHint;
            }

            if (heightHint != SWT.DEFAULT) {
                result.y = heightHint;
            }

            return result;
        }

        if (heightHint == SWT.DEFAULT) {
            if (preferredSize != null) {
                if (widthHint == preferredSize.x) {
                    return Geometry.copy(preferredSize);
                }
            }

            if (cachedHeight != null) {
                if (cachedHeight.x == widthHint) {
                    return Geometry.copy(cachedHeight);
                }
            }

            if (preferredWidthOrLargerIsMinimumHeight) {
                getPreferredSize();

                if (widthHint >= preferredSize.x) {
                    Point result = Geometry.copy(preferredSize);
                    result.x = widthHint;
                    return result;
                }
            }

            cachedHeight = computeSize(control, widthHint, heightHint);

            return Geometry.copy(cachedHeight);
        }

        if (widthHint == SWT.DEFAULT) {
            if (preferredSize != null) {
                if (heightHint == preferredSize.y) {
                    return Geometry.copy(preferredSize);
                }
            }

            if (cachedWidth != null) {
                if (cachedWidth.y == heightHint) {
                    return Geometry.copy(cachedWidth);
                }
            }

            cachedWidth = computeSize(control, widthHint, heightHint);

            return Geometry.copy(cachedWidth);
        }

        return computeSize(control, widthHint, heightHint);
    }

    /**
     * Compute the control's size, and ensure that non-default hints are returned verbatim
     * (this tries to compensate for SWT's hints, which aren't really the outer width of the
     * control).
     *
     * @param control
     * @param widthHint
     * @param heightHint
     * @return
     */
    private Point computeSize(Control control, int widthHint, int heightHint) {
        int adjustedWidthHint = widthHint == SWT.DEFAULT ? SWT.DEFAULT : Math
                .max(0, widthHint - widthAdjustment);
        int adjustedHeightHint = heightHint == SWT.DEFAULT ? SWT.DEFAULT : Math
                .max(0, heightHint - heightAdjustment);

        Point result = control.computeSize(adjustedWidthHint,
                adjustedHeightHint, flushChildren);
        flushChildren = false;


        if (widthHint != SWT.DEFAULT) {
            result.x = widthHint;
        }

        if (heightHint != SWT.DEFAULT) {
            result.y = heightHint;
        }

        return result;
    }

    /**
     * Returns true if the preferred length of the given control is
     * independent of the width and visa-versa. If this returns true,
     * then changing the widthHint argument to control.computeSize will
     * never change the resulting height and changing the heightHint
     * will never change the resulting width. Returns false if unknown.
     * <p>
     * This information can be used to improve caching. Incorrectly returning
     * a value of false may decrease performance, but incorrectly returning
     * a value of true will generate incorrect layouts... so always return
     * false if unsure.
     * </p>
     *
     * @param control
     * @return
     */
    static boolean independentLengthAndWidth(Control control) {
        if (control == null) {
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
        if (control instanceof Composite) {
            Composite composite = (Composite) control;
            Rectangle trim = composite.computeTrim(0, 0, 0, 0);

            widthAdjustment = trim.width;
            heightAdjustment = trim.height;
        } else {
            widthAdjustment = control.getBorderWidth() * 2;
            heightAdjustment = widthAdjustment;
        }
    }

    /**
     * Returns true only if the control will return a constant height for any
     * width hint larger than the preferred width. Returns false if there is
     * any situation in which the control does not have this property.
     *
     * <p>
     * Note: this method is only important for wrapping controls, and it can
     * safely return false for anything else. AFAIK, all SWT controls have this
     * property, but to be safe they will only be added to the list once the
     * property has been confirmed.
     * </p>
     *
     * @param control
     * @return
     */
    private static boolean isPreferredWidthMaximum(Control control) {
        return (control instanceof ToolBar
        || control instanceof Label);
    }
