
package com.couchbase.client.java.subdoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.core.message.kv.subdoc.multi.MultiLookupResponse;
import com.couchbase.client.core.message.kv.subdoc.multi.MultiResult;
import com.couchbase.client.core.message.kv.subdoc.multi.SubMultiLookupRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SimpleSubdocResponse;
import com.couchbase.client.core.message.kv.subdoc.simple.SubExistRequest;
import com.couchbase.client.core.message.kv.subdoc.simple.SubGetRequest;
import com.couchbase.client.deps.io.netty.util.internal.StringUtil;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.error.subdoc.DocumentNotJsonException;
import com.couchbase.client.java.error.subdoc.SubDocumentException;
import com.couchbase.client.java.transcoder.subdoc.FragmentTranscoder;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class AsyncLookupInBuilder {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(AsyncLookupInBuilder.class);

    private final ClusterFacade core;
    private final CouchbaseEnvironment environment;
    private final String bucketName;
    private final FragmentTranscoder subdocumentTranscoder;

    private final String docId;

    private final List<LookupSpec> specs;

    @InterfaceAudience.Private
    public AsyncLookupInBuilder(ClusterFacade core, String bucketName, CouchbaseEnvironment environment,
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
        this.specs = new ArrayList<LookupSpec>();
    }

    public Observable<DocumentFragment<Lookup>> doLookup() {
        if (specs.isEmpty()) {
            throw new IllegalArgumentException("Execution of a subdoc lookup requires at least one operation");
        } else if (specs.size() == 1) {
            return doSingleLookup(specs.get(0));
        } else {
            return doMultiLookup();
        }
    }

    public AsyncLookupInBuilder get(String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path is mandatory for subdoc get");
        }
        this.specs.add(new LookupSpec(Lookup.GET, path));
        return this;
    }

    public AsyncLookupInBuilder exists(String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path is mandatory for subdoc exists");
        }
        this.specs.add(new LookupSpec(Lookup.EXIST, path));
        return this;
    }

    protected Observable<DocumentFragment<Lookup>> doSingleLookup(LookupSpec spec) {
        if (spec.lookup() == Lookup.GET) {
            return getIn(docId, spec.path(), Object.class);
        } else if (spec.lookup() == Lookup.EXIST) {
            return existsIn(docId, spec.path());
        }
        return Observable.error(new UnsupportedOperationException("Lookup type " + spec.lookup() + " unknown"));
    }

    private final Func1<MultiResult<Lookup>, SubdocOperationResult<Lookup>> multiCoreResultToLookupResult
        = new Func1<MultiResult<Lookup>, SubdocOperationResult<Lookup>>() {
        @Override
        public SubdocOperationResult<Lookup> call(MultiResult<Lookup> lookupResult) {
            String path = lookupResult.path();
            Lookup operation = lookupResult.operation();
            ResponseStatus status = lookupResult.status();
            boolean isExist = operation == Lookup.EXIST;
            boolean isSuccess = status.isSuccess();
            boolean isNotFound = status == ResponseStatus.SUBDOC_PATH_NOT_FOUND;

            try {
                if (isExist && isSuccess) {
                    return SubdocOperationResult.createResult(path, operation, status, true);
                } else if (isExist && isNotFound) {
                    return SubdocOperationResult.createResult(path, operation, status, false);
                } else if (!isExist && isSuccess) {
                    try {
                        Object content = subdocumentTranscoder.decode(lookupResult.value(), Object.class);
                        return SubdocOperationResult.createResult(path, operation, status, content);
                    } catch (TranscodingException e) {
                        LOGGER.error("Couldn't decode multi-lookup " + operation + " for " + docId + "/" + path, e);
                        return SubdocOperationResult.createFatal(path, operation, e);
                    }
                } else if (!isExist && isNotFound) {
                    return SubdocOperationResult.createResult(path, operation, status, null);
                } else {
                    return SubdocOperationResult
                            .createError(path, operation, status, SubdocHelper.commonSubdocErrors(status, docId, path));
                }
            } finally {
                if (lookupResult.value() != null) {
                    lookupResult.value().release();
                }
            }
        }
    };

    protected Observable<DocumentFragment<Lookup>> doMultiLookup() {
        if (specs.isEmpty()) {
            throw new IllegalArgumentException("At least one Lookup Command is necessary for lookupIn");
        }
        final LookupSpec[] lookupSpecs = specs.toArray(new LookupSpec[specs.size()]);

        return Observable.defer(new Func0<Observable<MultiLookupResponse>>() {
            @Override
            public Observable<MultiLookupResponse> call() {
                return core.send(new SubMultiLookupRequest(docId, bucketName, lookupSpecs));
            }
        }).filter(new Func1<MultiLookupResponse, Boolean>() {
            @Override
            public Boolean call(MultiLookupResponse response) {
                if (response.status().isSuccess() || response.status() == ResponseStatus.SUBDOC_MULTI_PATH_FAILURE) {
                    return true;
                }

                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                throw SubdocHelper.commonSubdocErrors(response.status(), docId, "MULTI-LOOKUP");
            }
        }).flatMap(new Func1<MultiLookupResponse, Observable<MultiResult<Lookup>>>() {
            @Override
            public Observable<MultiResult<Lookup>> call(final MultiLookupResponse multiLookupResponse) {
                return Observable.from(multiLookupResponse.responses());
            }
        })
        .map(multiCoreResultToLookupResult)
        .toList()
        .map(new Func1<List<SubdocOperationResult<Lookup>>, DocumentFragment<Lookup>>() {
            @Override
            public DocumentFragment<Lookup> call(List<SubdocOperationResult<Lookup>> lookupResults) {
                return new DocumentFragment<Lookup>(docId, 0L, null, lookupResults);
            }
        });
    }


    private <T> Observable<DocumentFragment<Lookup>> getIn(final String id, final String path, final Class<T> fragmentType) {
        return Observable.defer(new Func0<Observable<SimpleSubdocResponse>>() {
            @Override
            public Observable<SimpleSubdocResponse> call() {
                SubGetRequest request = new SubGetRequest(id, path, bucketName);
                return core.send(request);
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Lookup>>() {
            @Override
            public DocumentFragment<Lookup> call(SimpleSubdocResponse response) {
                if (response.status().isSuccess()) {
                    try {
                        T content = subdocumentTranscoder.decodeWithMessage(response.content(), fragmentType,
                                "Couldn't decode subget fragment for " + id + "/" + path);
                        SubdocOperationResult<Lookup> single = SubdocOperationResult
                                .createResult(path, Lookup.GET, response.status(), content);
                        return new DocumentFragment<Lookup>(id, response.cas(), response.mutationToken(),
                                Collections.singletonList(single));
                    } finally {
                        if (response.content() != null) {
                            response.content().release();
                        }
                    }
                } else {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }

                    if (response.status() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                        SubdocOperationResult<Lookup> single = SubdocOperationResult
                                .createResult(path, Lookup.GET, response.status(), null);
                        return new DocumentFragment<Lookup>(id, response.cas(), response.mutationToken(), Collections.singletonList(single));
                    } else {
                        throw SubdocHelper.commonSubdocErrors(response.status(), id, path);
                    }
                }
            }
        });
    }

    private Observable<DocumentFragment<Lookup>> existsIn(final String id, final String path) {
        return Observable.defer(new Func0<Observable<SimpleSubdocResponse>>() {
            @Override
            public Observable<SimpleSubdocResponse> call() {
                SubExistRequest request = new SubExistRequest(id, path, bucketName);
                return core.send(request);
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Lookup>>() {
            @Override
            public DocumentFragment<Lookup> call(SimpleSubdocResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    SubdocOperationResult<Lookup> result = SubdocOperationResult
                            .createResult(path, Lookup.EXIST, response.status(), true);
                    return new DocumentFragment<Lookup>(docId, response.cas(), response.mutationToken(), Collections.singletonList(result));
                } else if (response.status() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                    SubdocOperationResult<Lookup> result = SubdocOperationResult
                            .createResult(path, Lookup.EXIST, response.status(), false);
                    return new DocumentFragment<Lookup>(docId, response.cas(), response.mutationToken(), Collections.singletonList(result));
                }

                throw SubdocHelper.commonSubdocErrors(response.status(), id, path);
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("lookupIn(").append(docId).append(")[");
        int pos = sb.length();
        for (LookupSpec spec : specs) {
            sb.append(", ").append(spec);
        }
        sb.delete(pos, pos+2);
        sb.append(']');
        return sb.toString();
    }
}
