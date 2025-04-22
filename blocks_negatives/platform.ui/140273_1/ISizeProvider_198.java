    /**
     * Returns a bitwise combination of flags indicating how and when computePreferredSize should
     * be used. When called with horizontal=true, this indicates the usage of computePreferredSize(true,...)
     * for computing widths. When called with horizontal=false, this indicates the usage of computeSize(false,...)
     * for computing heights. These flags are used for optimization. Each flag gives the part more control
     * over its preferred size but slows down the layout algorithm. Parts should return the minimum set
     * of flags necessary to specify their constraints.
     * <p>
     * If the return value of this function ever changes, the part must call <code>flushLayout</code> before
     * the changes will take effect.
     * </p>
     *
     * <ul>
     * <li>SWT.MAX: The part has a maximum size that will be returned by computePreferredSize(horizontal,
     * 	   INFINITE, someWidth, INFINITE)</li>
     * <li>SWT.MIN: The part has a minimum size that will be returned by computePreferredSize(horizontal,
     * 	   INFINITE, someWidth, 0)</li>
     * <li>SWT.WRAP: Indicates that computePreferredSize makes use of the availablePerpendicular argument. If this
     * 	   flag is not specified, then the third argument to computePreferredSize will always be set to
     *     INFINITE. The perpendicular size is expensive to compute, and it is usually only used
     *     for wrapping parts.
     * <li>SWT.FILL: The part may not return the preferred size verbatim when computePreferredSize is
     *     is given a value between the minimum and maximum sizes. This is commonly used if the part
     *     wants to use a set of predetermined sizes instead of using the workbench-provided size.
     *     For example, computePreferredSize(horizontal, availableSpace, someWidth,
     *     preferredSize) may return the nearest predetermined size. Note that this flag should
     *     be used sparingly. It can prevent layout caching and cause the workbench layout algorithm
     *     to degrade to exponential worst-case runtime. If this flag is omitted, then
     *     computePreferredSize may be used to compute the minimum and maximum sizes, but not for
     *     anything in between.</li>
     * </ul>
     *
     * @param width a value of true or false determines whether the return value applies when computing
     * widths or heights respectively. That is, getSizeFlags(true) will be used when calling
     * computePreferredSize(true,...)
     * @return any bitwise combination of SWT.MAX, SWT.MIN, SWT.WRAP, and SWT.FILL
     */
    int getSizeFlags(boolean width);
