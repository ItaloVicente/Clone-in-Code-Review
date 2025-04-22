
package org.eclipse.jgit.niofs.internal;

import java.nio.file.spi.FileSystemProvider;

import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
import org.eclipse.jgit.niofs.internal.security.FileSystemAuthorization;
import org.eclipse.jgit.niofs.internal.security.PublicKeyAuthenticator;

public abstract class SecuredFileSystemProvider extends FileSystemProvider {

    public abstract void setJAASAuthenticator(final AuthenticationService authenticator);

    public abstract void setHTTPAuthenticator(final AuthenticationService authenticator);

    public abstract void setSSHAuthenticator(final PublicKeyAuthenticator authenticator);

    public abstract void setAuthorizer(final FileSystemAuthorization authorizer);
}
