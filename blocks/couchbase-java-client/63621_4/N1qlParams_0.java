package com.couchbase.client.java;

import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.subdoc.DocumentFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MutationState implements Iterable<MutationToken> {

    private final List<MutationToken> tokens;

    private MutationState() {
        this.tokens = new ArrayList<MutationToken>();
    }

    public static MutationState from(Document... documents) {
        return new MutationState().add(documents);
    }

    public static MutationState from(DocumentFragment... documentFragments) {
        return new MutationState().add(documentFragments);
    }

    public MutationState add(Document... documents) {
        if (documents == null || documents.length == 0) {
            throw new IllegalArgumentException("At least one Document must be provided.");
        }
        for (Document d : documents) {
            addToken(d.mutationToken());
        }
        return this;
    }

    public MutationState add(DocumentFragment... documentFragments) {
        if (documentFragments == null || documentFragments.length == 0) {
            throw new IllegalArgumentException("At least one DocumentFragment must be provided.");
        }
        for (DocumentFragment d : documentFragments) {
            addToken(d.mutationToken());
        }
        return this;
    }

    public MutationState add(MutationState mutationState) {
        for(MutationToken token : mutationState) {
            addToken(token);
        }
        return this;
    }

    private void addToken(final MutationToken token) {
        if (token != null) {
            ListIterator<MutationToken> tokenIterator = tokens.listIterator();
            while (tokenIterator.hasNext()) {
                MutationToken t = tokenIterator.next();
                if (t.vbucketID() == token.vbucketID() && t.bucket().equals(token.bucket())) {
                    if (token.sequenceNumber() > t.sequenceNumber()) {
                        tokenIterator.set(token);
                    }
                    return;
                }
            }

            tokens.add(token);
        }
    }

    @Override
    public Iterator<MutationToken> iterator() {
        return tokens.iterator();
    }

    public JsonObject export() {
        JsonObject result = JsonObject.create();
        for (MutationToken token : tokens) {
            JsonObject bucket = result.getObject(token.bucket());
            if (bucket == null) {
                bucket = JsonObject.create();
                result.put(token.bucket(), bucket);
            }

            bucket.put(
                String.valueOf(token.vbucketID()),
                JsonArray.from(token.sequenceNumber(), String.valueOf(token.vbucketUUID()))
            );
        }
        return result;
    }
}
