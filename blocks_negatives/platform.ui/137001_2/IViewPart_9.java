 * As of 3.4, views may optionally adapt to {@link ISizeProvider} if they have
 * a preferred size. The default presentation will make a best effort to
 * allocate the preferred size to a view if it is the only part in a stack. If
 * there is more than one part in the stack, the constraints will be disabled
 * for that stack. The size constraints are adjusted for the size of the tab and
 * border trim. Note that this is considered to be a hint to the presentation,
 * and not all presentations may honor size constraints.
