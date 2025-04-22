package com.couchbase.client.java.util.features;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionTest {

    @Test
    public void shouldParseMajorMinorPatch() {
        Version version = Version.parseVersion("1.2.3");
        assertEquals("1.2.3", version.toString());
        assertEquals(1, version.major());
        assertEquals(2, version.minor());
        assertEquals(3, version.patch());
    }

    @Test
    public void shouldParseMajorMinor() {
        Version version = Version.parseVersion("1.2");
        assertEquals("1.2.0", version.toString());
        assertEquals(1, version.major());
        assertEquals(2, version.minor());
        assertEquals(0, version.patch());
    }

    @Test
    public void shouldParseMajor()  {
        Version version = Version.parseVersion("1");
        assertEquals("1.0.0", version.toString());
        assertEquals(1, version.major());
        assertEquals(0, version.minor());
        assertEquals(0, version.patch());
    }

    @Test
    public void shouldParseMajorMinorPatchGarbage() {
        Version version = Version.parseVersion("1.2.3z-5.4");
        assertEquals("1.2.3", version.toString());
        assertEquals(1, version.major());
        assertEquals(2, version.minor());
        assertEquals(3, version.patch());
    }

    @Test
    public void shouldParseMajorMinorGarbage() {
        Version version = Version.parseVersion("1.2z-5.4");
        assertEquals("1.2.0", version.toString());
        assertEquals(1, version.major());
        assertEquals(2, version.minor());
        assertEquals(0, version.patch());
    }

    @Test
    public void shouldParseMajorGarbage() {
        Version version = Version.parseVersion("1z-5.4");
        assertEquals("1.0.0", version.toString());
        assertEquals(1, version.major());
        assertEquals(0, version.minor());
        assertEquals(0, version.patch());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNotStartingWithMajor() {
        Version.parseVersion("a3.2.4");
    }

    @Test(expected = NullPointerException.class)
    public void shouldNullPointerOnNullVersionString() {
        Version.parseVersion(null);
    }

}
