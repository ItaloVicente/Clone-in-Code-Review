
package com.couchbase.client.core.env;

public class NetworkResolution {

    public static NetworkResolution DEFAULT = new NetworkResolution("default");

    public static NetworkResolution AUTO = new NetworkResolution("auto");

    public static NetworkResolution EXTERNAL = new NetworkResolution("external");

    private final String name;

    public static NetworkResolution custom(final String name) {
        return new NetworkResolution(name);
    }

    private NetworkResolution(final String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NetworkResolution that = (NetworkResolution) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NetworkResolution{" +
            "name='" + name + '\'' +
            '}';
    }
}
