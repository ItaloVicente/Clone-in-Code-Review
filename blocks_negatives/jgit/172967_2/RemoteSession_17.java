 * Create a remote "session" for executing remote commands.
 * <p>
 * Clients should subclass RemoteSession to create an alternate way for JGit to
 * execute remote commands. (The client application may already have this
 * functionality available.) Note that this class is just a factory for creating
 * remote processes. If the application already has a persistent connection to
 * the remote machine, RemoteSession may do nothing more than return a new
 * RemoteProcess when exec is called.
