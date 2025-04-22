
package org.eclipse.jgit.http.server;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.eclipse.jgit.util.HttpSupport.HDR_WWW_AUTHENTICATE;

public class WWWAuthenticationFilter implements Filter {

    private final String realmName;

    public WWWAuthenticationFilter(String realmName) {
        this.realmName = realmName;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request
            FilterChain chain) throws IOException

        try {
            chain.doFilter(request
        } finally {
            HttpServletResponse rsp = (HttpServletResponse) response;
            if (rsp.getStatus() == SC_UNAUTHORIZED && rsp.getHeader(HDR_WWW_AUTHENTICATE) == null) {
                rsp.setHeader(HDR_WWW_AUTHENTICATE
            }
        }
    }


}
