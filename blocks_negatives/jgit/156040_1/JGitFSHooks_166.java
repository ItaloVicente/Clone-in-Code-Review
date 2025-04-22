    private static final Logger LOGGER = LoggerFactory.getLogger(JGitFileSystemImpl.class);

    public static void executeFSHooks(Object fsHook, FileSystemHooks hookType, FileSystemHookExecutionContext ctx) {
        if (fsHook == null) {
            return;
        }
        if (fsHook instanceof List) {
            List hooks = (List) fsHook;
            hooks.forEach(h -> executeHook(h, hookType, ctx));
        } else {
            executeHook(fsHook, hookType, ctx);
        }
    }

    private static void executeHook(Object hook, FileSystemHooks hookType, FileSystemHookExecutionContext ctx) {
        if (hook instanceof FileSystemHooks.FileSystemHook) {
            FileSystemHooks.FileSystemHook fsHook = (FileSystemHooks.FileSystemHook) hook;
            fsHook.execute(ctx);
        } else {
            LOGGER.error("Error executing FS Hook FS " + hookType + " on " + ctx.getFsName() +
                                 ". Callback methods should implement FileSystemHooks.FileSystemHook. ");
        }
    }
