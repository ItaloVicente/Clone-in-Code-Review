package org.eclipse.core.databinding.bind;

import java.util.function.Function;

import org.eclipse.core.runtime.CoreException;

public interface IBidiConverter<T1, T2> {

	T2 modelToTarget(T1 fromObject);

	T1 targetToModel(T2 fromObject) throws CoreException;

	public static <T1, T2> IBidiConverter<T1, T2> create(
			Function<? super T1, ? extends T2> toTarget,
			ToModelFunction<? super T2, ? extends T1> toModel) {
		return new IBidiConverter<T1, T2>() {

			@Override
			public T2 modelToTarget(T1 fromObject) {
				return toTarget.apply(fromObject);
			}

			@Override
			public T1 targetToModel(T2 fromObject) throws CoreException {
				return toModel.convert(fromObject);
			}

		};
	}

	interface ToModelFunction<T, M> {
		public M convert(T target) throws CoreException;
	}
}
