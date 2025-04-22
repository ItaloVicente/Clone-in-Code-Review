 * Each IgnoreNode corresponds to one directory. Most IgnoreNodes will have
 * at most one source of ignore information -- its .gitignore file.
 * <br><br>
 * At the root of the repository, there may be an additional source of
 * ignore information (the exclude file)
 * <br><br>
 * It is recommended that implementers call the {@link #isIgnored(String)} method
 * rather than try to use the rules manually. The method will handle rule priority
 * automatically.
