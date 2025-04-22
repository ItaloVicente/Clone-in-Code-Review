    /**
     * Represents an observable that sends result chunks.
     */
    private UnicastAutoReleaseSubject<ByteBuf> queryRowObservable;

    /**
     * Represents an observable that has the signature of the N1QL results if there are any.
     */
    private UnicastAutoReleaseSubject<ByteBuf> querySignatureObservable;

    /**
     * Represents an observable that sends errors and warnings if any during query execution.
     */
    private UnicastAutoReleaseSubject<ByteBuf> queryErrorObservable;

    /**
     * Represent an observable that has the final execution status of the query, once all result rows and/or
     * errors/warnings have been sent.
     */
    private AsyncSubject<String> queryStatusObservable;

    /**
     * Represents an observable containing metrics on a terminated query.
     */
    private UnicastAutoReleaseSubject<ByteBuf> queryInfoObservable;

    /**
     * Represents the current query parsing state.
     */
    private byte queryParsingState = QUERY_STATE_INITIAL;

    /**
     * In case of chunked processing, allows to detect we are still parsing a section.
     */
    private boolean sectionDone = false;
