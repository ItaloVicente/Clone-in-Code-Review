
package org.eclipse.jgit.iplog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.util.HttpSupport;

class IPZillaQuery {
	private static final String RE_EPL = "^.*(Eclipse Public License|EPL).*$";

	private final URL base;

	private final String username;

	private final String password;

	private final ProxySelector proxySelector = ProxySelector.getDefault();

	IPZillaQuery(URL base
		this.base = base;
		this.username = username;
		this.password = password;
	}

	Collection<CQ> getCQs(Collection<Project> projects) throws IOException {
		try {
			login();
			Set<CQ> cqs = new HashSet<CQ>();
			for (Project project : projects)
				cqs.addAll(queryOneProject(project));
			return cqs;
		} finally {
			logout();
		}
	}

	private Set<CQ> queryOneProject(Project project) throws IOException {
		Map<String
		p.put("bugidtype"
		p.put("chfieldto"
		p.put("component"
		p.put("field-1-0-0"
		p.put("type-1-0-0"
		p.put("value-1-0-0"
		p.put("ctype"

		StringBuilder req = new StringBuilder();
		for (Map.Entry<String
			if (req.length() > 0)
				req.append('&');
			req.append(URLEncoder.encode(e.getKey()
			req.append('=');
			req.append(URLEncoder.encode(e.getValue()
		}
		URL csv = new URL(new URL(base

		req = new StringBuilder();
		for (String name : new String[] { "bug_severity"
				"resolution"
			if (req.length() > 0)
				req.append("%20");
			req.append(name);
		}
		setCookie(csv

		HttpURLConnection conn = open(csv);
		if (HttpSupport.response(conn) != HttpURLConnection.HTTP_OK) {
			throw new IOException("Query " + csv + " failed: "
					+ conn.getResponseCode() + " " + conn.getResponseMessage());
		}

		BufferedReader br = reader(conn);
		try {
			Set<CQ> cqs = new HashSet<CQ>();
			CSV in = new CSV(br);
			Map<String
			while ((row = in.next()) != null) {
				CQ cq = parseOneCQ(row);
				if (cq != null)
					cqs.add(cq);
			}
			return cqs;
		} finally {
			br.close();
		}
	}

	private BufferedReader reader(HttpURLConnection conn)
			throws UnsupportedEncodingException
		String encoding = conn.getContentEncoding();
		InputStream in = conn.getInputStream();
		if (encoding != null && !encoding.equals(""))
			return new BufferedReader(new InputStreamReader(in
		return new BufferedReader(new InputStreamReader(in));
	}

	private void login() throws MalformedURLException
			UnsupportedEncodingException
		final URL login = new URL(base
		StringBuilder req = new StringBuilder();
		req.append("Bugzilla_login=");
		req.append(URLEncoder.encode(username
		req.append('&');
		req.append("Bugzilla_password=");
		req.append(URLEncoder.encode(password
		byte[] reqbin = req.toString().getBytes("UTF-8");

		HttpURLConnection c = open(login);
		c.setDoOutput(true);
		c.setFixedLengthStreamingMode(reqbin.length);
		c.setRequestProperty(HttpSupport.HDR_CONTENT_TYPE
				"application/x-www-form-urlencoded");
		OutputStream out = c.getOutputStream();
		out.write(reqbin);
		out.close();

		if (HttpSupport.response(c) != HttpURLConnection.HTTP_OK) {
			throw new IOException("Login as " + username + " to " + login
					+ " failed: " + c.getResponseCode() + " "
					+ c.getResponseMessage());
		}
	}

	private void logout() throws MalformedURLException
			IOException {
		HttpSupport.response(open(new URL(base
	}

	private HttpURLConnection open(URL url) throws ConnectException
			IOException {
		Proxy proxy = HttpSupport.proxyFor(proxySelector
		HttpURLConnection c = (HttpURLConnection) url.openConnection(proxy);
		c.setUseCaches(false);
		return c;
	}

	private void setCookie(URL url
			throws IOException {
		Map<String
		cols.put("Set-Cookie"
		try {
			CookieHandler.getDefault().put(url.toURI()
		} catch (URISyntaxException e) {
			IOException err = new IOException("Invalid URI format:" + url);
			err.initCause(e);
			throw err;
		}
	}

	private CQ parseOneCQ(Map<String
		long id = Long.parseLong(row.get("bug_id"));
		String state = row.get("bug_severity");
		String bug_status = row.get("bug_status");
		String resolution = row.get("resolution");
		String short_desc = row.get("short_desc");
		String license = row.get("cf_license");

		Set<String> keywords = new TreeSet<String>();
		for (String w : row.get("keywords").split("
			keywords.add(w);

		if ("closed".equalsIgnoreCase(state)
				|| "rejected".equalsIgnoreCase(state)
				|| "withdrawn".equalsIgnoreCase(state))
			return null;

		if (!keywords.contains("nonepl") && license.matches(RE_EPL))
			return null;
		if (keywords.contains("epl"))
			return null;

		if ("new".equalsIgnoreCase(state)
				|| "under_review".equalsIgnoreCase(state)
				|| state.startsWith("awaiting_")) {
			if ("RESOLVED".equalsIgnoreCase(bug_status)
					|| "CLOSED".equalsIgnoreCase(bug_status)) {
				if ("FIXED".equalsIgnoreCase(resolution))
					state = "approved";
				else
					return null;
			}
		}

		StringBuilder use = new StringBuilder();
		for (String n : new String[] { "unmodified"
				"binary" }) {
			if (keywords.contains(n)) {
				if (use.length() > 0)
					use.append(' ');
				use.append(n);
			}
		}
		if (keywords.contains("sourceandbinary")) {
			if (use.length() > 0)
				use.append(' ');
			use.append("source & binary");
		}

		CQ cq = new CQ(id);
		cq.setDescription(short_desc);
		cq.setLicense(license);
		cq.setState(state);
		if (use.length() > 0)
			cq.setUse(use.toString().trim());
		return cq;
	}
}
