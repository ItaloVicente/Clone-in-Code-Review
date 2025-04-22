package com.couchbase.client.java.cluster.api;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpMethod;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public abstract class AbstractClusterApiClient<T extends IRestBuilder> {

    protected final String username;
    protected final String password;
    protected final ClusterFacade core;

    protected AbstractClusterApiClient(String username, String password, ClusterFacade core) {
        this.username = username;
        this.password = password;
        this.core = core;
    }

    public T get(String... paths) {
        return createBuilder(HttpMethod.GET, buildPath(paths));
    }

    public T post(String... paths) {
        return createBuilder(HttpMethod.POST, buildPath(paths));
    }

    public T put(String... paths) {
        return createBuilder(HttpMethod.PUT, buildPath(paths));
    }

    public T delete(String... paths) {
        return createBuilder(HttpMethod.DELETE, buildPath(paths));
    }

    protected abstract T createBuilder(HttpMethod method, String fullPath);

    public static String buildPath(String... paths) {
        if (paths == null || paths.length == 0) {
            throw new IllegalArgumentException("Path must at least contain one element");
        }

        StringBuilder path = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            String p = paths[i];
            if (p == null) continue;

            if (p.charAt(0) != '/') {
                path.append('/');
            }
            if (p.charAt(p.length() - 1) == '/') {
                path.append(p, 0, p.length() - 1);
            } else {
                path.append(p);
            }
        }
        return path.toString();
    }

}
