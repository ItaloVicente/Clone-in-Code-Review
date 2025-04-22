		Callable<PullResult> setup = () -> {
                    StoredConfig config = dbTarget.getConfig();
                    config.setString("pull"
                    config.save();
                    return target.pull().call();
                };
