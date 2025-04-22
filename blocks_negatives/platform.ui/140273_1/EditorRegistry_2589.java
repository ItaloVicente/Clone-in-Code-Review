            merge.putAll(map);
            Collection<FileEditorMapping> values = merge.values();
            FileEditorMapping result[] = new FileEditorMapping[values.size()];
            return values.toArray(result);
        }

        /**
         * Return all user mappings.
         *
         * @return the mappings
         */
        public FileEditorMapping[] userMappings() {
            Collection<FileEditorMapping> values = map.values();
            FileEditorMapping result[] = new FileEditorMapping[values.size()];
            return values.toArray(result);
        }
    }

    @Override
