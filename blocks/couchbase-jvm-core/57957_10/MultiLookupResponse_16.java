
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class LookupResult {

    private final short statusCode;
    private final ResponseStatus status;
    private final String path;
    private final Lookup operation;
    private final ByteBuf value;

    public LookupResult(short statusCode, ResponseStatus status, String path, Lookup operation, ByteBuf value) {
        this.statusCode = statusCode;
        this.status = status;
        this.path = path;
        this.operation = operation;
        this.value = value;
    }

    public short statusCode() {
        return statusCode;
    }

    public ResponseStatus status() {
        return status;
    }

    public String path() {
        return path;
    }

    public Lookup operation() {
        return operation;
    }

    public ByteBuf value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LookupResult that = (LookupResult) o;

        if (statusCode != that.statusCode) return false;
        if (status != that.status) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (operation != that.operation) return false;
        if (value == null) return that.value == null;

        return value.toString(CharsetUtil.UTF_8).equals(that.value.toString(CharsetUtil.UTF_8));

    }

    @Override
    public int hashCode() {
        int result = (int) statusCode;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        if (value != null) {
            result = 31 * result + value.toString(CharsetUtil.UTF_8).hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(operation())
                .append('(').append(path()).append(") = ")
                .append(status());
        if (value.readableBytes() > 0) {
            builder.append(": ").append(value().toString(CharsetUtil.UTF_8));
        }
        return builder.toString();
    }
}
