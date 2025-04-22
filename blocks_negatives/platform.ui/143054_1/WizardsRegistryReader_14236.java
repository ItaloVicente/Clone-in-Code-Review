        private Category category;

        private String path;

        CategoryNode(Category cat) {
            category = cat;
            String[] categoryPath = category.getParentPath();
            if (categoryPath != null) {
                for (String parentPath : categoryPath) {
                    path += parentPath + '/';
                }
            }
            path += cat.getId();
        }

        String getPath() {
            return path;
        }

        Category getCategory() {
            return category;
        }
    }

    private static final Comparator comparer = new Comparator() {
        private Collator collator = Collator.getInstance();

        @Override
