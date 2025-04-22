package org.eclipse.jgit.internal.transport.sshd;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jgit.annotations.NonNull;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public class GssApiMechanisms {

	private GssApiMechanisms() {
	}




	private static final Object LOCK = new Object();

	private static Map<Oid

	@NonNull
	public static Collection<Oid> getSupportedMechanisms() {
		synchronized (LOCK) {
			if (supportedMechanisms == null) {
				GSSManager manager = GSSManager.getInstance();
				Oid[] mechs = manager.getMechs();
				Map<Oid
				if (mechs != null) {
					for (Oid oid : mechs) {
						mechanisms.put(oid
					}
				}
				supportedMechanisms = mechanisms;
			}
			return Collections.unmodifiableSet(supportedMechanisms.keySet());
		}
	}

	public static void worked(@NonNull Oid mechanism) {
		synchronized (LOCK) {
			supportedMechanisms.put(mechanism
		}
	}

	public static void failed(@NonNull Oid mechanism) {
		synchronized (LOCK) {
			Boolean worked = supportedMechanisms.get(mechanism);
			if (worked != null && !worked.booleanValue()) {
				supportedMechanisms.remove(mechanism);
			}
		}
	}

	public static InetAddress resolve(@NonNull InetSocketAddress remote) {
		InetAddress address = remote.getAddress();
		if (address == null) {
			try {
				address = InetAddress.getByName(remote.getHostString());
			} catch (UnknownHostException e) {
				return null;
			}
		}
		return address;
	}

	@NonNull
	public static String getCanonicalName(@NonNull InetSocketAddress remote) {
		InetAddress address = resolve(remote);
		if (address == null) {
			return remote.getHostString();
		}
		return address.getCanonicalHostName();
	}

	public static GSSContext createContext(@NonNull Oid mechanism
			@NonNull String fqdn) {
		GSSContext context = null;
		try {
			GSSManager manager = GSSManager.getInstance();
			context = manager.createContext(
					manager.createName(
							GssApiMechanisms.GSSAPI_HOST_PREFIX + fqdn
							GSSName.NT_HOSTBASED_SERVICE)
					mechanism
		} catch (GSSException e) {
			closeContextSilently(context);
			failed(mechanism);
			return null;
		}
		worked(mechanism);
		return context;
	}

	public static void closeContextSilently(GSSContext context) {
		if (context != null) {
			try {
				context.dispose();
			} catch (GSSException e) {
			}
		}
	}

	private static Oid createOid(String rep) {
		try {
			return new Oid(rep);
		} catch (GSSException e) {
			return null;
		}
	}

}
