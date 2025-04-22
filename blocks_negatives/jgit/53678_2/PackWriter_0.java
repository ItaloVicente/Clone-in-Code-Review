 * Typical usage consists of creating instance intended for some pack,
 * configuring options, preparing the list of objects by calling
 * {@link #preparePack(Iterator)} or
 * {@link #preparePack(ProgressMonitor, Set, Set)}, and finally producing the
 * stream with
 * {@link #writePack(ProgressMonitor, ProgressMonitor, OutputStream)}.
