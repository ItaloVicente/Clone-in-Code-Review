		Callable<PullResult> setup = () -> {
                    StoredConfig config = dbTarget.getConfig();
                    config.setString("pull"
                    config.setString("branch"
                    config.save();
                    return target.pull().call();
                };
