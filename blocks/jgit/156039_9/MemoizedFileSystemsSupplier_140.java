package org.eclipse.jgit.niofs.internal.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public class MemoizedFileSystemsSupplier<T> implements Supplier<T> {

	final Supplier<T> delegate;
	ConcurrentMap<Class<?>

	private MemoizedFileSystemsSupplier(Supplier<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public T get() {

		T t = this.map.computeIfAbsent(MemoizedFileSystemsSupplier.class
		return t;
	}

	public static <T> Supplier<T> of(Supplier<T> provider) {
		return new MemoizedFileSystemsSupplier<>(provider);
	}
}
