    boolean exists(String id);

    boolean exists(String id, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> boolean exists(D document);

    <D extends Document<?>> boolean exists(D document, long timeout, TimeUnit timeUnit);

