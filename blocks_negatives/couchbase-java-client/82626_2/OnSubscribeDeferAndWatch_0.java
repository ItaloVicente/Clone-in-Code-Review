        if (!(o instanceof AsyncSubject)) {
            Exceptions.throwOrReport(
                new IllegalStateException("Only AsyncSubject is allowed with deferAndWatch (is "
                    + o.getClass().getSimpleName() + ")"), s);
            return;
        }

