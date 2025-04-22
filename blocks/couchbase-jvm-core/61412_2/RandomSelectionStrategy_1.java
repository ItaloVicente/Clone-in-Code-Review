    private static final ThreadLocal<Random> RANDOM = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random();
        }
    };
