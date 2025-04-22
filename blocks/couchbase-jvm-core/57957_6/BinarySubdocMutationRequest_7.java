
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.kv.AbstractKeyValueRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public abstract class AbstractSubdocRequest extends AbstractKeyValueRequest implements BinarySubdocRequest {

    public static final NullPointerException EXCEPTION_NULL_PATH = new NullPointerException("Path is mandatory");

    public static final IllegalArgumentException EXCEPTION_EMPTY_PATH = new IllegalArgumentException("Path cannot be empty");

    private final String path;
    private final int pathLength;
    private final ByteBuf content;

    public AbstractSubdocRequest(String key, String path, String bucket, ByteBuf... restOfContent) {
        this(key, path, bucket, AsyncSubject.<CouchbaseResponse>create(), restOfContent);
    }

    public AbstractSubdocRequest(String key, String path, String bucket,
                                 Subject<CouchbaseResponse, CouchbaseResponse> observable,
                                 ByteBuf... restOfContent) {
        super(key, bucket, null, observable);
        this.path = path;
        ByteBuf pathByteBuf;
        if (path == null || path.isEmpty()) {
            pathByteBuf = Unpooled.EMPTY_BUFFER;
        } else {
            pathByteBuf = Unpooled.wrappedBuffer(path.getBytes(CharsetUtil.UTF_8));
        }
        this.pathLength = pathByteBuf.readableBytes();
        this.content = createContent(pathByteBuf, restOfContent);

        if (this.path == null) {
            cleanUpAndThrow(EXCEPTION_NULL_PATH);
        }
    }

    protected void cleanUpAndThrow(RuntimeException e) {
        if (content != null && content.refCnt() > 0) {
            content.release();
        }
        throw e;
    }

    protected ByteBuf createContent(ByteBuf pathByteBuf, ByteBuf... restOfContent) {
        if (restOfContent == null || restOfContent.length == 0) {
            return pathByteBuf;
        } else {
            CompositeByteBuf composite = Unpooled.compositeBuffer(1 + restOfContent.length);
            composite.addComponent(pathByteBuf);
            composite.writerIndex(composite.writerIndex() + pathByteBuf.readableBytes());

            for (ByteBuf component : restOfContent) {
                composite.addComponent(component);
                composite.writerIndex(composite.writerIndex() + component.readableBytes());
            }

            return composite;
        }
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public int pathLength() {
        return this.pathLength;
    }

    @Override
    public ByteBuf content() {
        return this.content;
    }
}
