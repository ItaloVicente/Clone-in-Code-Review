 *	MemcachedClient c=new MemcachedClient(
 *		new InetSocketAddress("hostname", portNum));
 *
 *	c.set("someKey", 3600, someObject);
 *	Object myObject=c.get("someKey");
 *	</pre>
 *
 *	<h2>Advanced Usage</h2>
 *
 *	<p>
 *	 MemcachedClient may be processing a great deal of asynchronous messages or
 *	 possibly dealing with an unreachable memcached, which may delay processing.
 *	 If a memcached is disabled, for example, MemcachedConnection will continue
 *	 to attempt to reconnect and replay pending operations until it comes back
 *	 up.  To prevent this from causing your application to hang, you can use
 *	 one of the asynchronous mechanisms to time out a request and cancel the
 *	 operation to the server.
 *	</p>
 *
 *	<pre>
