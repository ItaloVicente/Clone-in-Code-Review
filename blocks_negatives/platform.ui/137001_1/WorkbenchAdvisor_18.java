 * <li><code>preWindowOpen</code> - called as each window is being opened;
 * use to configure aspects of the window other than actions bars </li>
 * <li><code>fillActionBars</code> - called after <code>preWindowOpen</code>
 * to configure a window's action bars</li>
 * <li><code>postWindowRestore</code> - called after a window has been
 * recreated from a previously saved state; use to adjust the restored window</li>
 * <li><code>postWindowCreate</code> - called after a window has been
 * created, either from an initial state or from a restored state; used to
 * adjust the window</li>
 * <li><code>openIntro</code> - called immediately before a window is opened
 * in order to create the introduction component, if any.</li>
 * <li><code>postWindowOpen</code> - called after a window has been opened;
 * use to hook window listeners, etc.</li>
 * <li><code>preWindowShellClose</code> - called when a window's shell is
 * closed by the user; use to pre-screen window closings</li>
