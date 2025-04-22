
package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.RemoveRequest;
import com.couchbase.client.core.message.kv.RemoveResponse;
import com.couchbase.client.core.message.kv.subdoc.simple.SimpleSubdocResponse;
import com.couchbase.client.core.message.kv.subdoc.simple.SubDictAddRequest;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ResourceLeakDetector;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SubdocumentDocumentFlagsTests extends ClusterDependentTest {

    static {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
    }

    @BeforeClass
    public static void checkExtendedAttributeAvailable() throws Exception {
        connect(false);
        assumeMinimumVersionCompatible(5, 0);
    }


    @Test
    public void shouldCreateDocumentIfSet() {
        String subPath = "hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldCreateDocumentIfSet", subPath, fragment, bucket());
        insertRequest.createDocument(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldCreateDocumentIfSet", bucket())).toBlocking().single();
        assert(response.status() == ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldCreateDocumentIfSetWithExpiryAndPathFlags() {
        String subPath = "first.hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);
        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldCreateDocumentIfSetWithExpiryAndPathFlags", subPath, fragment, bucket(), 10, 0);
        insertRequest.createDocument(true);
        insertRequest.createIntermediaryPath(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldCreateDocumentIfSetWithExpiryAndPathFlags", bucket())).toBlocking().single();
        assert(response.status() == ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldFailIfCreateDocumentIsNotSetWhenDocumentDoesNotExist() {
        String subPath = "hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);
        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldFailIfCreateDocumentIsNotSetWhenDocumentDoesNotExist", subPath, fragment, bucket());
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assert(insertResponse.status() == ResponseStatus.NOT_EXISTS);
    }

}
