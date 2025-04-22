		Callable<PullResult> setup = () -> {
                    StoredConfig config = dbTarget.getConfig();
                    config.setString("branch"
                    config.save();
                    return target.pull().call();
                };
