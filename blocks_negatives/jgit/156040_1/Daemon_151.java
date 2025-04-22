        uploadPackFactory = (req, db) -> {
            final UploadPack up = new UploadPack(db);
            up.setTimeout(getTimeout());
            up.setRefFilter(new HiddenBranchRefFilter());
            final PackConfig config = new PackConfig(db);
            config.setCompressionLevel(Deflater.BEST_COMPRESSION);
            up.setPackConfig(config);
