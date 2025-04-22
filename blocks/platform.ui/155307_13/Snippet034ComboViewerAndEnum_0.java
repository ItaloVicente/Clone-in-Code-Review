
package org.eclipse.core.databinding.conversion;

import java.util.Objects;

public class EnumConverters {
	public static <T extends Enum<T>> IConverter<Integer, T> fromOrdinal(Class<T> enumToType) {
		Objects.requireNonNull(enumToType);
		T[] ordinals = enumToType.getEnumConstants();
		return IConverter.create(Integer.class, enumToType,
				i -> i == null || i < 0 || i >= ordinals.length ? null : ordinals[i]);
	}

	public static <T extends Enum<T>> IConverter<String, T> fromString(Class<T> enumToType) {
		Objects.requireNonNull(enumToType);
		return IConverter.create(String.class, enumToType, text -> {
			if (text == null) {
				return null;
			}

			try {
				return Enum.valueOf(enumToType, text);
			} catch (IllegalArgumentException e) {
				return null;
			}
		});
	}

	public static <T extends Enum<T>> IConverter<T, Integer> toOrdinal(Class<T> enumFromType) {
		Objects.requireNonNull(enumFromType);
		return IConverter.create(enumFromType, Integer.class, e -> e == null ? null : e.ordinal());
	}

	public static <T extends Enum<T>> IConverter<T, String> toString(Class<T> enumFromType) {
		Objects.requireNonNull(enumFromType);
		return IConverter.create(enumFromType, String.class, e -> e == null ? null : e.toString());
	}
}
