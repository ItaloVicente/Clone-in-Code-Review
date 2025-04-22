package com.couchbase.client.java.util.features;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable<Version> {

    public static final Version NO_VERSION = new Version(0, 0, 0);

    private final int major;
    private final int minor;
    private final int patch;

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public int major() {
        return major;
    }

    public int minor() {
        return minor;
    }

    public int patch() {
        return patch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Version version = (Version) o;

        if (major != version.major) {
            return false;
        }
        if (minor != version.minor) {
            return false;
        }
        if (patch != version.patch) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + patch;
        return result;
    }

    @Override
    public int compareTo(Version o) {
        if (o == null) {
            return 1;
        }
        int ma = this.major - o.major;
        int mi = this.minor - o.minor;
        int pa = this.patch - o.patch;

        if (ma != 0) {
            return ma;
        } else if (mi != 0) {
            return mi;
        } else {
            return pa;
        }
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

    private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?.*");

    public static final Version parseVersion(String versionString) {
        if (versionString == null) {
            throw new NullPointerException();
        }

        Matcher matcher = VERSION_PATTERN.matcher(versionString);
        if (matcher.matches() && matcher.groupCount() == 3) {
            int major = Integer.parseInt(matcher.group(1));
            int minor = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 0;
            int patch = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
            return new Version(major, minor, patch);
        } else {
            throw new IllegalArgumentException(
                    "Expected a version string starting with X[.Y[.Z]], was " + versionString);
        }
    }

}
