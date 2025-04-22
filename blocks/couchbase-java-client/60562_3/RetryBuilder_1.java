    protected static class InversePredicate implements Func1<Throwable, Boolean> {

        private final Func1<Throwable, Boolean> predicate;

        public InversePredicate(final Func1<Throwable, Boolean> predicate) {
            this.predicate = predicate;
        }

        @Override
        public Boolean call(Throwable throwable) {
            Boolean toInvert = predicate.call(throwable);
            if (toInvert == null) {
                return null;
            } else if (Boolean.TRUE.equals(toInvert)) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
    }

