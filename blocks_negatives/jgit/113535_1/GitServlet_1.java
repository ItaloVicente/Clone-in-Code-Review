 * servlet by taking advantage of its extension from {@link MetaServlet}.
 * Callers may register their own URL suffix translations through
 * {@link #serve(String)}, or their regex translations through
 * {@link #serveRegex(String)}. Each translation should contain a complete
 * filter pipeline which ends with the HttpServlet that should handle the
 * requested action.
