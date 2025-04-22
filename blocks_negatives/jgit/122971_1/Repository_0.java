 * <p>
 * <i>Note to implementors:</i> Make sure to override
 * {@link #notifyIndexChanged(boolean)} or {@link #notifyIndexChanged()}, or
 * else both will throw {@code StackOverflowException}. In the next JGit minor
 * release, {@link #notifyIndexChanged(boolean)} will be abstract and {@link
 * #notifyIndexChanged()} will be final.
