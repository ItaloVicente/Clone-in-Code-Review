
package com.couchbase.client.java.subdoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.core.message.kv.subdoc.multi.MultiMutationResponse;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.core.message.kv.subdoc.multi.MutationCommand;
import com.couchbase.client.core.message.kv.subdoc.multi.SubMultiMutationRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.AbstractSubdocMutationRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SimpleSubdocResponse;
import com.couchbase.client.core.message.kv.subdoc.simple.SubArrayRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SubCounterRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SubDeleteRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SubDictAddRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SubDictUpsertRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SubReplaceRequest;
import com.couchbase.client.core.message.observe.Observe;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.deps.io.netty.util.internal.StringUtil;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.error.CASMismatchException;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.error.DurabilityException;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.error.subdoc.CannotInsertValueException;
import com.couchbase.client.java.error.subdoc.DocumentNotJsonException;
import com.couchbase.client.java.error.subdoc.MultiMutationException;
import com.couchbase.client.java.error.subdoc.PathExistsException;
import com.couchbase.client.java.error.subdoc.PathInvalidException;
import com.couchbase.client.java.error.subdoc.PathMismatchException;
import com.couchbase.client.java.error.subdoc.PathNotFoundException;
import com.couchbase.client.java.error.subdoc.ZeroDeltaException;
import com.couchbase.client.java.transcoder.subdoc.FragmentTranscoder;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;


@InterfaceStability.Experimental
@InterfaceAudience.Public
public class AsyncMutateInBuilder {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(AsyncMutateInBuilder.class);

    private final ClusterFacade core;
    private final CouchbaseEnvironment environment;
    private final String bucketName;
    private final FragmentTranscoder subdocumentTranscoder;

    protected final String docId;
    protected final List<MutationSpec> mutationSpecs;

    protected int expiry;
    protected long cas;
    protected PersistTo persistTo;
    protected ReplicateTo replicateTo;

    @InterfaceAudience.Private
    public AsyncMutateInBuilder(ClusterFacade core, String bucketName, CouchbaseEnvironment environment,
            FragmentTranscoder transcoder, String docId) {
        if (docId == null || docId.isEmpty()) {
            throw new IllegalArgumentException("The document ID must not be null or empty.");
        }
        if (docId.getBytes().length > 250) {
            throw new IllegalArgumentException("The document ID must not be larger than 250 bytes");
        }

        this.core = core;
        this.bucketName = bucketName;
        this.environment = environment;
        this.subdocumentTranscoder = transcoder;
        this.docId = docId;
        this.mutationSpecs = new ArrayList<MutationSpec>();

        this.expiry = 0;
        this.cas = 0L;
        this.persistTo = PersistTo.NONE;
        this.replicateTo = ReplicateTo.NONE;
    }

    public Observable<DocumentFragment<Mutation>> doMutate() {
        if (mutationSpecs.isEmpty()) {
            throw new IllegalArgumentException("Execution of a subdoc mutation requires at least one operation");
        } else if (mutationSpecs.size() == 1) { //FIXME implement single path optim
            return doSingleMutate(mutationSpecs.get(0));
        } else {
            return doMultiMutate();
        }
    }

    public AsyncMutateInBuilder withExpiry(int expiry) {
        this.expiry = expiry;
        return this;
    }

    public AsyncMutateInBuilder withCas(long cas) {
        this.cas = cas;
        return this;
    }

    public AsyncMutateInBuilder withDurability(PersistTo persistTo) {
        this.persistTo = persistTo;
        return this;
    }

    public AsyncMutateInBuilder withDurability(ReplicateTo replicateTo) {
        this.replicateTo = replicateTo;
        return this;
    }

    public AsyncMutateInBuilder withDurability(PersistTo persistTo, ReplicateTo replicateTo) {
        this.persistTo = persistTo;
        this.replicateTo = replicateTo;
        return this;
    }

    public <T> AsyncMutateInBuilder replace(String path, T fragment) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for replace");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.REPLACE, path, fragment, false));
        return this;
    }

    public <T> AsyncMutateInBuilder insert(String path, T fragment, boolean createParents) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for insert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_ADD, path, fragment, createParents));
        return this;
    }

    public <T> AsyncMutateInBuilder upsert(String path, T fragment, boolean createParents) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for upsert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_UPSERT, path, fragment, createParents));
        return this;
    }

    public <T> AsyncMutateInBuilder remove(String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for remove");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DELETE, path, null, false));
        return this;
    }

    public AsyncMutateInBuilder counter(String path, long delta, boolean createParents) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for counter");
        }
        if (delta == 0L) {
            throw new ZeroDeltaException();
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.COUNTER, path, delta, createParents));
        return this;
    }

    public <T> AsyncMutateInBuilder extend(String path, T value, ExtendDirection direction, boolean createParents) {
        if (direction == ExtendDirection.FRONT) {
            this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, value, createParents));
        } else {
            this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_LAST, path, value, createParents));
        }
        return this;
    }

    public <T> AsyncMutateInBuilder arrayInsert(String path, T value) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for arrayInsert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_INSERT, path, value, false));
        return this;
    }

    public <T> AsyncMutateInBuilder addUnique(String path, T value, boolean createParents) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_ADD_UNIQUE, path, value, createParents));
        return this;
    }


    protected Observable<DocumentFragment<Mutation>> doMultiMutate() {
        if (mutationSpecs.isEmpty()) {
            throw new IllegalArgumentException("At least one Mutation Spec is necessary for mutateIn");
        }

        Observable<DocumentFragment<Mutation>> mutations = Observable.defer(new Func0<Observable<MutationCommand>>() {
            @Override
            public Observable<MutationCommand> call() {
                List<ByteBuf> bufList = new ArrayList<ByteBuf>(mutationSpecs.size());
                final List<MutationCommand> commands = new ArrayList<MutationCommand>(mutationSpecs.size());

                for (int i = 0; i < mutationSpecs.size(); i++) {
                    MutationSpec spec = mutationSpecs.get(i);
                    if (spec.type() == Mutation.DELETE) {
                        commands.add(new MutationCommand(Mutation.DELETE, spec.path()));
                    } else {
                        try {
                            ByteBuf buf = subdocumentTranscoder.encodeWithMessage(spec.fragment(), "Couldn't encode MutationSpec #" +
                                    i + " (" + spec.type() + " on " + spec.path() + ") in " + docId);
                            bufList.add(buf);
                            commands.add(new MutationCommand(spec.type(), spec.path(), buf, spec.createParents()));
                        } catch (TranscodingException e) {
                            releaseAll(bufList);
                            return Observable.error(e);
                        }
                    }
                }
                return Observable.from(commands);
            }
        }).toList()
        .flatMap(new Func1<List<MutationCommand>, Observable<MultiMutationResponse>>(){
            @Override
            public Observable<MultiMutationResponse> call(List<MutationCommand> mutationCommands) {
                return core.send(new SubMultiMutationRequest(docId, bucketName, expiry, cas, mutationCommands));
            }
        }).flatMap(new Func1<MultiMutationResponse, Observable<DocumentFragment<Mutation>>>() {
            @Override
            public Observable<DocumentFragment<Mutation>> call(MultiMutationResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    int resultSize = response.responses().size();
                    List<MultiResult<Mutation>> results = new ArrayList<MultiResult<Mutation>>(resultSize);
                    for (com.couchbase.client.core.message.kv.subdoc.multi.MultiResult<Mutation> result : response.responses()) {
                        try {
                            Object content = null;
                            if (result.value().isReadable()) {
                                content = subdocumentTranscoder.decode(result.value(), Object.class);
                            }
                            results.add(MultiResult.createResult(result.path(), result.operation(), result.status(), content));
                        } catch (TranscodingException e) {
                            LOGGER.error("Couldn't decode multi-lookup " + result.operation() + " for " + docId + "/" + result.path(), e);
                            results.add(MultiResult.createFatal(result.path(), result.operation(), e));
                        } finally {
                            if (result.value() != null) {
                                result.value().release();
                            }
                        }
                    }
                    return Observable.just(
                            new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), results));
                }

                switch(response.status()) {
                    case SUBDOC_MULTI_PATH_FAILURE:
                        int index = response.firstErrorIndex();
                        ResponseStatus errorStatus = response.firstErrorStatus();
                        String errorPath = mutationSpecs.get(index).path();
                        CouchbaseException errorException = SubdocHelper.commonSubdocErrors(errorStatus, docId, errorPath);

                        return Observable.error(new MultiMutationException(index, errorStatus, mutationSpecs, errorException));
                    default:
                        return Observable.error(SubdocHelper.commonSubdocErrors(response.status(), docId, "MULTI-MUTATION"));
                }
            }
        });

        return subdocObserveMutation(mutations);
    }

    protected Observable<DocumentFragment<Mutation>> doSingleMutate(MutationSpec spec) {
        Observable<DocumentFragment<Mutation>> mutation;
        switch (spec.type()) {
            case DICT_UPSERT:
                mutation = doSingleMutate(spec, DICT_UPSERT_FACTORY, DICT_UPSERT_EVALUATOR);
                break;
            case DICT_ADD:
                mutation = doSingleMutate(spec, DICT_ADD_FACTORY, DICT_ADD_EVALUATOR);
                break;
            case REPLACE:
                mutation = doSingleMutate(spec, REPLACE_FACTORY, REPLACE_EVALUATOR);
                break;
            case ARRAY_PUSH_FIRST:
            case ARRAY_PUSH_LAST:
                mutation = doSingleMutate(spec, ARRAY_EXTEND_FACTORY, ARRAY_EXTEND_EVALUATOR);
                break;
            case ARRAY_INSERT:
                mutation = doSingleMutate(spec, ARRAY_INSERT_FACTORY, ARRAY_INSERT_EVALUATOR);
                break;
            case ARRAY_ADD_UNIQUE:
                mutation = doSingleMutate(spec, ARRAY_ADDUNIQUE_FACTORY, ARRAY_ADDUNIQUE_EVALUATOR);
                break;
            case COUNTER:
                mutation = counterIn(spec);
                break;
            case DELETE:
                mutation = removeIn(spec);
                break;
            default:
                mutation = Observable.error(new UnsupportedOperationException());
                break;
        }
        return subdocObserveMutation(mutation);
    }


    private final Func2<MutationSpec, ByteBuf, SubDictUpsertRequest> DICT_UPSERT_FACTORY =
            new Func2<MutationSpec, ByteBuf, SubDictUpsertRequest>() {
                @Override
                public SubDictUpsertRequest call(MutationSpec spec, ByteBuf buf) {
                    SubDictUpsertRequest request = new SubDictUpsertRequest(docId, spec.path(), buf, bucketName, expiry, cas);
                    request.createIntermediaryPath(spec.createParents());
                    return request;
                }
            };
    private static final Func3<ResponseStatus, String, String, Object> DICT_UPSERT_EVALUATOR =
            new Func3<ResponseStatus, String, String, Object>() {
                @Override
                public Object call(ResponseStatus status, String docId, String path) {
                    switch(status) {
                        case SUCCESS:
                            return null;
                        case SUBDOC_PATH_INVALID:
                            return new PathInvalidException("Path " + path + " ends in an array index in "
                                    + docId + ", expected dictionary");
                        case SUBDOC_PATH_MISMATCH:
                            return new PathMismatchException("Path " + path + " ends in a scalar value in "
                                    + docId + ", expected dictionary");
                        default:
                            return SubdocHelper.throwIfDocumentLevelError(status, docId, path);
                    }
                }
            };

    private final Func2<MutationSpec, ByteBuf, SubDictAddRequest> DICT_ADD_FACTORY =
            new Func2<MutationSpec, ByteBuf, SubDictAddRequest>() {
                @Override
                public SubDictAddRequest call(MutationSpec spec, ByteBuf buf) {
                    SubDictAddRequest request = new SubDictAddRequest(docId, spec.path(), buf, bucketName, expiry, cas);
                    request.createIntermediaryPath(spec.createParents());
                    return request;
                }
            };
    private static final Func3<ResponseStatus, String, String, Object> DICT_ADD_EVALUATOR =
            new Func3<ResponseStatus, String, String, Object>() {
                @Override
                public Object call(ResponseStatus status, String docId, String path) {
                    switch(status) {
                        case SUCCESS:
                            return null;
                        case SUBDOC_PATH_INVALID:
                            return new PathInvalidException("Path " + path + " ends in an array index in "
                                    + docId + ", expected dictionary");
                        case SUBDOC_PATH_MISMATCH:
                            return new PathMismatchException("Path " + path + " ends in a scalar value in "
                                    + docId + ", expected dictionary");
                        case SUBDOC_PATH_EXISTS:
                            return new PathExistsException(docId, path);
                        default:
                            return SubdocHelper.throwIfDocumentLevelError(status, docId, path);
                    }
                }
            };

    private final Func2<MutationSpec, ByteBuf, SubReplaceRequest> REPLACE_FACTORY =
            new Func2<MutationSpec, ByteBuf, SubReplaceRequest>() {
                @Override
                public SubReplaceRequest call(MutationSpec spec, ByteBuf buf) {
                    SubReplaceRequest request = new SubReplaceRequest(docId, spec.path(), buf, bucketName, expiry, cas);
                    request.createIntermediaryPath(spec.createParents());
                    return request;
                }
            };
    private static final Func3<ResponseStatus, String, String, Object> REPLACE_EVALUATOR =
            new Func3<ResponseStatus, String, String, Object>() {
                @Override
                public Object call(ResponseStatus status, String docId, String path) {
                    switch(status) {
                        case SUCCESS:
                            return null;
                        case SUBDOC_PATH_NOT_FOUND:
                            return new PathNotFoundException("Path to be replaced " + path + " not found in " + docId);
                        case SUBDOC_PATH_MISMATCH:
                            return new PathMismatchException("Path " + path + " ends in a scalar value in "
                                    + docId + ", expected dictionary");
                        default:
                            return SubdocHelper.throwIfDocumentLevelError(status, docId, path);
                    }
                }
            };

    private final Func2<MutationSpec, ByteBuf, SubArrayRequest> ARRAY_EXTEND_FACTORY =
            new Func2<MutationSpec, ByteBuf, SubArrayRequest>() {
                @Override
                public SubArrayRequest call(MutationSpec spec, ByteBuf buf) {
                    SubArrayRequest.ArrayOperation op;
                    switch (spec.type()) {
                        case ARRAY_PUSH_FIRST:
                            op = SubArrayRequest.ArrayOperation.PUSH_FIRST;
                            break;
                        case ARRAY_PUSH_LAST:
                        default:
                            op = SubArrayRequest.ArrayOperation.PUSH_LAST;
                            break;
                    }

                    SubArrayRequest request = new SubArrayRequest(docId, spec.path(), op,
                            buf, bucketName, expiry, cas);
                    request.createIntermediaryPath(spec.createParents());
                    return request;
                }
            };
    private static final Func3<ResponseStatus, String, String, Object> ARRAY_EXTEND_EVALUATOR =
            new Func3<ResponseStatus, String, String, Object>() {
                @Override
                public Object call(ResponseStatus status, String docId, String path) {
                    if (status.isSuccess()) {
                        return null;
                    } else {
                        return SubdocHelper.throwIfDocumentLevelError(status, docId, path);
                    }
                }
            };

    private final Func2<MutationSpec, ByteBuf, SubArrayRequest> ARRAY_INSERT_FACTORY =
            new Func2<MutationSpec, ByteBuf, SubArrayRequest>() {
                @Override
                public SubArrayRequest call(MutationSpec spec, ByteBuf buf) {
                    return new SubArrayRequest(docId, spec.path(),
                            SubArrayRequest.ArrayOperation.INSERT, buf, bucketName, expiry, cas);
                }
            };
    private static final Func3<ResponseStatus, String, String, Object> ARRAY_INSERT_EVALUATOR =
            new Func3<ResponseStatus, String, String, Object>() {
                @Override
                public Object call(ResponseStatus status, String docId, String path) {
                    switch (status) {
                        case SUCCESS:
                            return null;
                        case SUBDOC_PATH_MISMATCH:
                            return new PathMismatchException("The last component of path " + path
                                    + " in " + docId + " was expected to be an array element");
                        default:
                            return SubdocHelper.throwIfDocumentLevelError(status, docId, path);
                    }
                }
            };

    private final Func2<MutationSpec, ByteBuf, SubArrayRequest> ARRAY_ADDUNIQUE_FACTORY =
            new Func2<MutationSpec, ByteBuf, SubArrayRequest>() {
                @Override
                public SubArrayRequest call(MutationSpec spec, ByteBuf buf) {
                    SubArrayRequest request = new SubArrayRequest(docId, spec.path(),
                            SubArrayRequest.ArrayOperation.ADD_UNIQUE, buf, bucketName, expiry, cas);
                    request.createIntermediaryPath(spec.createParents());
                    return request;
                }
            };
    private static final Func3<ResponseStatus, String, String, Object> ARRAY_ADDUNIQUE_EVALUATOR =
            new Func3<ResponseStatus, String, String, Object>() {
                @Override
                public Object call(ResponseStatus status, String docId, String path) {
                    switch (status) {
                        case SUCCESS:
                            return null;
                        case SUBDOC_PATH_EXISTS:
                            return new PathExistsException("The unique value already exist in array " + path
                                    + " in document " + docId);
                        case SUBDOC_VALUE_CANTINSERT:
                            return new CannotInsertValueException("The unique value provided is not a JSON primitive");
                        case SUBDOC_PATH_MISMATCH:
                            return new PathMismatchException("The array at " + path
                                    + " contains non-primitive JSON elements in document " + docId);
                        default:
                            return SubdocHelper.throwIfDocumentLevelError(status, docId, path);
                    }
                }
            };

    private Observable<DocumentFragment<Mutation>> doSingleMutate(final MutationSpec spec,
            final Func2<MutationSpec, ByteBuf, ? extends AbstractSubdocMutationRequest> requestFactory,
            final Func3<ResponseStatus, String, String, Object> responseStatusDocIdAndPathToValueEvaluator) {
        return Observable.defer(new Func0<Observable<SimpleSubdocResponse>>() {
            @Override
            public Observable<SimpleSubdocResponse> call() {
                ByteBuf buf;
                try {
                    buf = subdocumentTranscoder.encodeWithMessage(spec.fragment(),
                            "Couldn't encode subdoc fragment " + docId + "/" + spec.path() +
                            " \"" + spec.fragment() + "\"");
                } catch (TranscodingException e) {
                    return Observable.error(e);
                }

                return core.send(requestFactory.call(spec, buf));
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Mutation>>() {
            @Override
            public DocumentFragment<Mutation> call(SimpleSubdocResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                Object value = responseStatusDocIdAndPathToValueEvaluator.call(response.status(), docId, spec.path());
                MultiResult<Mutation> singleResult = MultiResult.createResult(spec.path(), spec.type(), response.status(), value);
                return new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), Collections.singletonList(singleResult));
            }
        });
    }

    private Observable<DocumentFragment<Mutation>> removeIn(final MutationSpec spec) {
        return Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        SubDeleteRequest request = new SubDeleteRequest(docId, spec.path(), bucketName, expiry, cas);
                        return core.send(request);
                    }
                })
                .map(new Func1<SimpleSubdocResponse, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(SimpleSubdocResponse response) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }

                        Object value;
                        if (response.status().isSuccess()) {
                            value = null;
                        } else {
                            value = SubdocHelper.throwIfDocumentLevelError(response.status(), docId, spec.path());
                        }
                        MultiResult<Mutation> singleResult = MultiResult.createResult(spec.path(), spec.type(), response.status(), value);
                        return new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), Collections.singletonList(singleResult));
                    }
                });
    }

    private Observable<DocumentFragment<Mutation>> counterIn(final MutationSpec spec) {
        if (spec.fragment() != null && !(spec.fragment() instanceof Number)) {
            return Observable.error(new IllegalArgumentException("Counter fragment must be a long/integer"));
        }
        Number fragment = (Number) spec.fragment();
        if (fragment == null || fragment.longValue() == 0L) {
            return Observable.error(new ZeroDeltaException());
        }

        final long delta = fragment.longValue();

        return Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        SubCounterRequest request = new SubCounterRequest(docId, spec.path(), delta, bucketName, expiry, cas);
                        request.createIntermediaryPath(spec.createParents());
                        return core.send(request);
                    }
                })
                .map(new Func1<SimpleSubdocResponse, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(SimpleSubdocResponse response) {

                        ResponseStatus status = response.status();
                        Object value;
                        if (status.isSuccess()) {
                            try {
                                value = Long.parseLong(response.content().toString(CharsetUtil.UTF_8));
                            } catch (NumberFormatException e) {
                                value = new TranscodingException("Couldn't parse counter response into a long", e);
                                status = ResponseStatus.FAILURE;
                            } finally {
                                if (response.content() != null) {
                                    response.content().release();
                                }
                            }
                        } else {
                            if (response.content() != null) {
                                response.content().release();
                            }
                            value = SubdocHelper.throwIfDocumentLevelError(status, docId, spec.path());
                        }

                        MultiResult<Mutation> singleResult = MultiResult.createResult(spec.path(), spec.type(), status, value);
                        return new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), Collections.singletonList(singleResult));
                    }
                });
    }


    private <T> Observable<DocumentFragment<T>> subdocObserveMutation(Observable<DocumentFragment<T>> mutation) {
        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return mutation;
        }

        return mutation.flatMap(new Func1<DocumentFragment<T>, Observable<DocumentFragment<T>>>() {
            @Override
            public Observable<DocumentFragment<T>> call(final DocumentFragment<T> frag) {
                return Observe
                    .call(core, bucketName, frag.id(), frag.cas(), false, frag.mutationToken(), persistTo.value(), replicateTo.value(),
                        environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, DocumentFragment<T>>() {
                        @Override
                        public DocumentFragment<T> call(Boolean aBoolean) {
                            return frag;
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<DocumentFragment<T>>>() {
                        @Override
                        public Observable<DocumentFragment<T>> call(Throwable throwable) {
                            return Observable.error(new DurabilityException(
                                "Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    });
            }
        });
    }

    private static void releaseAll(List<ByteBuf> byteBufs) {
        for (ByteBuf byteBuf : byteBufs) {
            if (byteBuf != null && byteBuf.refCnt() > 0) {
                byteBuf.release();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("mutateIn(").append(docId);
        if (expiry != 0)
            sb.append(", expiry=").append(expiry);

        if (cas != 0L)
            sb.append(", cas=").append(cas);

        if (persistTo != PersistTo.NONE)
            sb.append(", persistTo=").append(persistTo);

        if (replicateTo != ReplicateTo.NONE)
            sb.append(", replicateTo=").append(replicateTo);

        sb.append(")[");
        int pos = sb.length();
        for (MutationSpec mutationSpec : mutationSpecs) {
            sb.append(", ").append(mutationSpec);
        }
        sb.delete(pos, pos+2);
        sb.append(']');
        return sb.toString();
    }
}
