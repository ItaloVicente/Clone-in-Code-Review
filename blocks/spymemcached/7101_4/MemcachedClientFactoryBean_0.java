package net.spy.memcached.protocol;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import net.spy.memcached.MemcachedNode;
import net.spy.memcached.compat.SpyObject;
import net.spy.memcached.ops.Operation;

public abstract class TCPNodeImpl<T> extends SpyObject
	implements MemcachedNode<T> {

	private final SocketAddress socketAddress;
	private final ByteBuffer rbuf;
	private final ByteBuffer wbuf;
	protected final BlockingQueue<T> writeQ;
	protected final BlockingQueue<T> readQ;
	protected final BlockingQueue<T> inputQueue;
	private volatile int reconnectAttempt=1;
	private SocketChannel channel;
	protected int toWrite=0;
	protected T optimizedOp=null;
	private volatile SelectionKey sk=null;
	protected final boolean shouldAuth;
	protected CountDownLatch authLatch;
	private ArrayList<T> reconnectBlocked;

	private final AtomicInteger continuousTimeout = new AtomicInteger(0);


	public TCPNodeImpl(SocketAddress sa, SocketChannel c,
			int bufSize, BlockingQueue<T> rq,
			BlockingQueue<T> wq, BlockingQueue<T> iq,
			boolean waitForAuth) {
		super();
		assert sa != null : "No SocketAddress";
		assert c != null : "No SocketChannel";
		assert bufSize > 0 : "Invalid buffer size: " + bufSize;
		assert rq != null : "No operation read queue";
		assert wq != null : "No operation write queue";
		assert iq != null : "No input queue";
		socketAddress=sa;
		setChannel(c);
		rbuf=ByteBuffer.allocate(bufSize);
		wbuf=ByteBuffer.allocate(bufSize);
		getWbuf().clear();
		readQ=rq;
		writeQ=wq;
		inputQueue=iq;
		shouldAuth = waitForAuth;
		setupForAuth();
	}

	public final void copyInputQueue() {
		Collection<T> tmp=new ArrayList<T>();

		inputQueue.drainTo(tmp, writeQ.remainingCapacity());

		writeQ.addAll(tmp);
	}

	public Collection<T> destroyInputQueue() {
		Collection<T> rv=new ArrayList<T>();
		inputQueue.drainTo(rv);
		return rv;
	}

	public final void transitionWriteItem() {
		T op=removeCurrentWriteOp();
		assert op != null : "There is no write item to transition";
		getLogger().debug("Finished writing %s", op);
	}

	protected abstract void optimize();

	public final T getCurrentReadOp() {
		return readQ.peek();
	}

	public final T removeCurrentReadOp() {
		return readQ.remove();
	}

	public final T getCurrentWriteOp() {
		return optimizedOp == null ? writeQ.peek() : optimizedOp;
	}

	public final T removeCurrentWriteOp() {
		T rv=optimizedOp;
		if(rv == null) {
			rv=writeQ.remove();
		} else {
			optimizedOp=null;
		}
		return rv;
	}

	public final boolean hasReadOp() {
		return !readQ.isEmpty();
	}

	public final boolean hasWriteOp() {
		return !(optimizedOp == null && writeQ.isEmpty());
	}

	public final void insertOp(T op) {
		ArrayList<T> tmp = new ArrayList<T>(
				inputQueue.size() + 1);
		tmp.add(op);
		inputQueue.drainTo(tmp);
		inputQueue.addAll(tmp);
	}

	public final int getSelectionOps() {
		int rv=0;
		if(getChannel().isConnected()) {
			if(hasReadOp()) {
				rv |= SelectionKey.OP_READ;
			}
			if(toWrite > 0 || hasWriteOp()) {
				rv |= SelectionKey.OP_WRITE;
			}
		} else {
			rv = SelectionKey.OP_CONNECT;
		}
		return rv;
	}

	public final ByteBuffer getRbuf() {
		return rbuf;
	}

	public final ByteBuffer getWbuf() {
		return wbuf;
	}

	public final SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public final boolean isActive() {
		return reconnectAttempt == 0
			&& getChannel() != null && getChannel().isConnected();
	}

	public final void reconnecting() {
		reconnectAttempt++;
		continuousTimeout.set(0);
	}

	public final void connected() {
		reconnectAttempt=0;
		continuousTimeout.set(0);
	}

	public final int getReconnectCount() {
		return reconnectAttempt;
	}

	@Override
	public final String toString() {
		int sops=0;
		if(getSk()!= null && getSk().isValid()) {
			sops=getSk().interestOps();
		}
		int rsize=readQ.size() + (optimizedOp == null ? 0 : 1);
		int wsize=writeQ.size();
		int isize=inputQueue.size();
		return "{QA sa=" + getSocketAddress() + ", #Rops=" + rsize
			+ ", #Wops=" + wsize
			+ ", #iq=" + isize
			+ ", topRop=" + getCurrentReadOp()
			+ ", topWop=" + getCurrentWriteOp()
			+ ", toWrite=" + toWrite
			+ ", interested=" + sops + "}";
	}

	public final void registerChannel(SocketChannel ch, SelectionKey skey) {
		setChannel(ch);
		setSk(skey);
	}

	public final void setChannel(SocketChannel to) {
		assert channel == null || !channel.isOpen()
			: "Attempting to overwrite channel";
		channel = to;
	}

	public final SocketChannel getChannel() {
		return channel;
	}

	public final void setSk(SelectionKey to) {
		sk = to;
	}

	public final SelectionKey getSk() {
		return sk;
	}

	public final int getBytesRemainingToWrite() {
		return toWrite;
	}

	public final int writeSome() throws IOException {
		int wrote=channel.write(wbuf);
		assert wrote >= 0 : "Wrote negative bytes?";
		toWrite -= wrote;
		assert toWrite >= 0
			: "toWrite went negative after writing " + wrote
				+ " bytes for " + this;
		getLogger().debug("Wrote %d bytes", wrote);
		return wrote;
	}


	public void setContinuousTimeout(boolean timedOut) {
		if (timedOut && isActive()) {
			continuousTimeout.incrementAndGet();
		} else {
			continuousTimeout.set(0);
		}
	}

	public int getContinuousTimeout() {
		return continuousTimeout.get();
	}


	public final void fixupOps() {
		SelectionKey s = sk;
		if(s != null && s.isValid()) {
			int iops=getSelectionOps();
			getLogger().debug("Setting interested opts to %d", iops);
			s.interestOps(iops);
		} else {
			getLogger().debug("Selection key is not valid.");
		}
	}

	public final void authComplete() {
		if (reconnectBlocked != null && reconnectBlocked.size() > 0 ) {
		    inputQueue.addAll(reconnectBlocked);
		}
		authLatch.countDown();
	}

	public final void setupForAuth() {
		if (shouldAuth) {
			authLatch = new CountDownLatch(1);
			if (inputQueue.size() > 0) {
				reconnectBlocked = new ArrayList<T>(
				inputQueue.size() + 1);
				inputQueue.drainTo(reconnectBlocked);
			}
			assert(inputQueue.size() == 0);
			setupResend();
		} else {
			authLatch = new CountDownLatch(0);
		}
	}

	public abstract void setupResend();
	
	public abstract void fillWriteBuffer(boolean shouldOptimize);
	
	public abstract void addOp(Operation op);
	
}
