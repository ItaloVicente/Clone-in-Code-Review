package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.deps.com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.couchbase.client.java.CouchbaseAsyncBucket;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class JsonFunctions {

    public static Expression decodeJson(Expression expression) {
        return x("DECODE_JSON(" + expression.toString() + ")");
    }

    public static Expression decodeJson(JsonObject json) {
        char[] encoded = JsonStringEncoder.getInstance().quoteAsString(json.toString());
        return x("DECODE_JSON(\"" + new String(encoded) + "\")");
    }

    public static Expression decodeJson(String jsonString) {
        try {
            JsonObject jsonObject = CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.stringToJsonObject(jsonString);
            return decodeJson(jsonObject);
        } catch (Exception e) {
            throw new IllegalArgumentException("String is not representing JSON object: " + jsonString);
        }
    }

    public static Expression encodeJson(Expression expression) {
        return x("ENCODE_JSON(" + expression.toString() + ")");
    }

    public static Expression encodeJson(String expression) {
        return encodeJson(x(expression));
    }

    public static Expression encodedSize(Expression expression) {
        return x("ENCODED_SIZE(" + expression.toString() + ")");
    }

    public static Expression encodedSize(String expression) {
        return encodedSize(x(expression));
    }

    public static Expression polyLength(Expression expression) {
        return x("POLY_LENGTH(" + expression.toString() + ")");
    }

    public static Expression polyLength(String expression) {
        return polyLength(x(expression));
    }

}
