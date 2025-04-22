/*
 * Copyright (c) 2016 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.couchbase.client.java;

import com.couchbase.client.core.CouchbaseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionStringTest {

    @Test
    public void shouldParseValidSchemes() {
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertTrue(parsed.hosts().isEmpty());
        assertTrue(parsed.params().isEmpty());

        assertEquals(ConnectionString.Scheme.COUCHBASES, parsed.scheme());
        assertTrue(parsed.hosts().isEmpty());
        assertTrue(parsed.params().isEmpty());

        assertEquals(ConnectionString.Scheme.HTTP, parsed.scheme());
        assertTrue(parsed.hosts().isEmpty());
        assertTrue(parsed.params().isEmpty());
    }

    @Test(expected = CouchbaseException.class)
    public void shouldFailOnInvalidScheme() {
    }

    @Test
    public void shouldParseHostList() {
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertTrue(parsed.params().isEmpty());
        assertEquals(1, parsed.hosts().size());
        assertEquals("localhost", parsed.hosts().get(0).getHostName());
        assertEquals(0, parsed.hosts().get(0).getPort());

        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertTrue(parsed.params().isEmpty());
        assertEquals(1, parsed.hosts().size());
        assertEquals("localhost", parsed.hosts().get(0).getHostName());
        assertEquals(1234, parsed.hosts().get(0).getPort());

        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertTrue(parsed.params().isEmpty());
        assertEquals(2, parsed.hosts().size());
        assertEquals("foo", parsed.hosts().get(0).getHostName());
        assertEquals(1234, parsed.hosts().get(0).getPort());
        assertEquals("bar", parsed.hosts().get(1).getHostName());
        assertEquals(5678, parsed.hosts().get(1).getPort());

        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertTrue(parsed.params().isEmpty());
        assertEquals(3, parsed.hosts().size());
        assertEquals("foo", parsed.hosts().get(0).getHostName());
        assertEquals(0, parsed.hosts().get(0).getPort());
        assertEquals("bar", parsed.hosts().get(1).getHostName());
        assertEquals(5678, parsed.hosts().get(1).getPort());
        assertEquals("baz", parsed.hosts().get(2).getHostName());
        assertEquals(0, parsed.hosts().get(2).getPort());
    }

    @Test
    public void shouldParseParams() {
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals(1, parsed.hosts().size());
        assertEquals(1, parsed.params().size());
        assertEquals("bar", parsed.params().get("foo"));

        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals(1, parsed.hosts().size());
        assertEquals(2, parsed.params().size());
        assertEquals("bar", parsed.params().get("foo"));
        assertEquals("true", parsed.params().get("setting"));
    }

}
