package org.eclipse.core.databinding.conversion;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumConverters {
	public static <T extends Enum<T>> IConverter<Integer, T> fromOrdinal(Class<T> enumToType) {
		Map<Integer, T> convertionMap = Arrays.stream(enumToType.getEnumConstants())
				.collect(Collectors.toMap(Enum::ordinal, e -> e));
		return IConverter.create(Integer.class, enumToType, convertionMap::get);
	}

	public static <T extends Enum<T>> IConverter<String, T> fromString(Class<T> enumToType) {
		Map<String, T> convertionMap = Arrays.stream(enumToType.getEnumConstants())
				.collect(Collectors.toMap(Enum::toString, e -> e));
		return IConverter.create(String.class, enumToType, convertionMap::get);
	}

	public static <T extends Enum<T>> IConverter<T, Integer> toOrdinal(Class<T> enumFromType) {
		return IConverter.create(enumFromType, Integer.class, e -> e == null ? null : e.ordinal());
	}

	public static <T extends Enum<T>> IConverter<T, String> toString(Class<T> enumFromType) {
		return IConverter.create(enumFromType, String.class, e -> e == null ? null : e.toString());
	}
}
