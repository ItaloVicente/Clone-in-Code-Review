    @Test(expected = RequestTooBigException.class)
    public void shouldFailStoringLargeDoc() {
        int size = 21000000;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < size; i++) {
            buffer.append('l');
        }

        bucket().upsert(RawJsonDocument.create("tooLong", buffer.toString()));
    }

    @Test(expected = RequestTooBigException.class)
    public void shouldFailAppendWhenTooLarge() {
        String id = "longAppend";

        int size = 5000000;
        StringBuilder chunk = new StringBuilder();
        for (int i = 0; i < size; i++) {
            chunk.append('l');
        }

        bucket().upsert(StringDocument.create(id, "a"));

        for(int i = 0; i < 5; i++) {
            bucket().append(StringDocument.create(id,chunk.toString()));
        }
    }

    @Test(expected = RequestTooBigException.class)
    public void shouldFailPrependWhenTooLarge() {
        String id = "longPrepend";

        int size = 5000000;
        StringBuilder chunk = new StringBuilder();
        for (int i = 0; i < size; i++) {
            chunk.append('l');
        }

        bucket().upsert(StringDocument.create(id, "a"));

        for(int i = 0; i < 5; i++) {
            bucket().prepend(StringDocument.create(id, chunk.toString()));
        }
    }

