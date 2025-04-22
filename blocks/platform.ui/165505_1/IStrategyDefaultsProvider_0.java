
package org.eclipse.core.databinding;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.conversion.NumberToStringConverter;
import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.core.databinding.util.Policy;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.ClassLookupSupport;
import org.eclipse.core.internal.databinding.Pair;
import org.eclipse.core.internal.databinding.conversion.CharacterToStringConverter;
import org.eclipse.core.internal.databinding.conversion.IdentityConverter;
import org.eclipse.core.internal.databinding.conversion.IntegerToStringConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToBigDecimalConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToBigIntegerConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToByteConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToDoubleConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToFloatConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToIntegerConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToLongConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToNumberConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToShortConverter;
import org.eclipse.core.internal.databinding.conversion.ObjectToStringConverter;
import org.eclipse.core.internal.databinding.conversion.StringToByteConverter;
import org.eclipse.core.internal.databinding.conversion.StringToCharacterConverter;
import org.eclipse.core.internal.databinding.conversion.StringToDateConverter;
import org.eclipse.core.internal.databinding.conversion.StringToShortConverter;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter;
import org.eclipse.core.internal.databinding.validation.NumberToByteValidator;
import org.eclipse.core.internal.databinding.validation.NumberToDoubleValidator;
import org.eclipse.core.internal.databinding.validation.NumberToFloatValidator;
import org.eclipse.core.internal.databinding.validation.NumberToIntegerValidator;
import org.eclipse.core.internal.databinding.validation.NumberToLongValidator;
import org.eclipse.core.internal.databinding.validation.NumberToShortValidator;
import org.eclipse.core.internal.databinding.validation.NumberToUnboundedNumberValidator;
import org.eclipse.core.internal.databinding.validation.ObjectToPrimitiveValidator;
import org.eclipse.core.internal.databinding.validation.StringToByteValidator;
import org.eclipse.core.internal.databinding.validation.StringToCharacterValidator;
import org.eclipse.core.internal.databinding.validation.StringToDateValidator;
import org.eclipse.core.internal.databinding.validation.StringToDoubleValidator;
import org.eclipse.core.internal.databinding.validation.StringToFloatValidator;
import org.eclipse.core.internal.databinding.validation.StringToIntegerValidator;
import org.eclipse.core.internal.databinding.validation.StringToLongValidator;
import org.eclipse.core.internal.databinding.validation.StringToShortValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.ibm.icu.text.NumberFormat;

public class CoreStrategyDefaultsProvider implements IStrategyDefaultsProvider {
	private static ValidatorRegistry validatorRegistry = new ValidatorRegistry();

	private static HashMap validatorsByConverter = new HashMap();

	@Override
	public StrategyDefaults getDefaults(Object sourceType, Object destinationType) {
		IConverter converter = createConverter(sourceType, destinationType);

		if (converter != null) {
			IValidator validator = createValidator(converter, sourceType,
					destinationType);
			return new StrategyDefaults(converter, validator);
		}

		return null;
	}

	private IValidator createValidator(IConverter converter, Object fromType,
			Object toType) {
		if (fromType == null || toType == null) {
			return new IValidator() {

				@Override
				public IStatus validate(Object value) {
					return Status.OK_STATUS;
				}
			};
		}

		return findValidator(converter, fromType, toType);
	}

	private IValidator findValidator(IConverter converter, Object fromType,
			Object toType) {
		IValidator result = null;

		if (String.class.equals(fromType)) {
			result = (IValidator) validatorsByConverter.get(converter);

			if (result == null) {
				if (Integer.class.equals(toType) || Integer.TYPE.equals(toType)) {
					result = new StringToIntegerValidator(
							(NumberFormatConverter) converter);
				} else if (Long.class.equals(toType)
						|| Long.TYPE.equals(toType)) {
					result = new StringToLongValidator(
							(NumberFormatConverter) converter);
				} else if (Float.class.equals(toType)
						|| Float.TYPE.equals(toType)) {
					result = new StringToFloatValidator(
							(NumberFormatConverter) converter);
				} else if (Double.class.equals(toType)
						|| Double.TYPE.equals(toType)) {
					result = new StringToDoubleValidator(
							(NumberFormatConverter) converter);
				} else if (Byte.class.equals(toType)
						|| Byte.TYPE.equals(toType)) {
					result = new StringToByteValidator(
							(NumberFormatConverter) converter);
				} else if (Short.class.equals(toType)
						|| Short.TYPE.equals(toType)) {
					result = new StringToShortValidator(
							(NumberFormatConverter) converter);
				} else if (Character.class.equals(toType)
						|| Character.TYPE.equals(toType)
						&& converter instanceof StringToCharacterConverter) {
					result = new StringToCharacterValidator(
							(StringToCharacterConverter) converter);
				} else if (Date.class.equals(toType)
						&& converter instanceof StringToDateConverter) {
					result = new StringToDateValidator(
							(StringToDateConverter) converter);
				}

				if (result != null) {
					validatorsByConverter.put(converter, result);
				}
			}
		} else if (converter instanceof NumberToNumberConverter) {
			result = (IValidator) validatorsByConverter.get(converter);

			if (result == null) {
				if (converter instanceof NumberToByteConverter) {
					result = new NumberToByteValidator(
							(NumberToByteConverter) converter);
				} else if (converter instanceof NumberToShortConverter) {
					result = new NumberToShortValidator(
							(NumberToShortConverter) converter);
				} else if (converter instanceof NumberToIntegerConverter) {
					result = new NumberToIntegerValidator(
							(NumberToIntegerConverter) converter);
				} else if (converter instanceof NumberToLongConverter) {
					result = new NumberToLongValidator(
							(NumberToLongConverter) converter);
				} else if (converter instanceof NumberToFloatConverter) {
					result = new NumberToFloatValidator(
							(NumberToFloatConverter) converter);
				} else if (converter instanceof NumberToDoubleConverter) {
					result = new NumberToDoubleValidator(
							(NumberToDoubleConverter) converter);
				} else if (converter instanceof NumberToBigIntegerConverter
						|| converter instanceof NumberToBigDecimalConverter) {
					result = new NumberToUnboundedNumberValidator(
							(NumberToNumberConverter) converter);
				}
			}
		}

		if (result == null) {
			result = validatorRegistry.get(fromType, toType);
		}

		return result;
	}

	private static class ValidatorRegistry {

		private HashMap validators = new HashMap();

		private ValidatorRegistry() {
			associate(Integer.class, Integer.TYPE,
					new ObjectToPrimitiveValidator(Integer.TYPE));
			associate(Byte.class, Byte.TYPE, new ObjectToPrimitiveValidator(
					Byte.TYPE));
			associate(Short.class, Short.TYPE, new ObjectToPrimitiveValidator(
					Short.TYPE));
			associate(Long.class, Long.TYPE, new ObjectToPrimitiveValidator(
					Long.TYPE));
			associate(Float.class, Float.TYPE, new ObjectToPrimitiveValidator(
					Float.TYPE));
			associate(Double.class, Double.TYPE,
					new ObjectToPrimitiveValidator(Double.TYPE));
			associate(Boolean.class, Boolean.TYPE,
					new ObjectToPrimitiveValidator(Boolean.TYPE));

			associate(Object.class, Integer.TYPE,
					new ObjectToPrimitiveValidator(Integer.TYPE));
			associate(Object.class, Byte.TYPE, new ObjectToPrimitiveValidator(
					Byte.TYPE));
			associate(Object.class, Short.TYPE, new ObjectToPrimitiveValidator(
					Short.TYPE));
			associate(Object.class, Long.TYPE, new ObjectToPrimitiveValidator(
					Long.TYPE));
			associate(Object.class, Float.TYPE, new ObjectToPrimitiveValidator(
					Float.TYPE));
			associate(Object.class, Double.TYPE,
					new ObjectToPrimitiveValidator(Double.TYPE));
			associate(Object.class, Boolean.TYPE,
					new ObjectToPrimitiveValidator(Boolean.TYPE));
		}

		private void associate(Object fromClass, Object toClass,
				IValidator validator) {
			validators.put(new Pair(fromClass, toClass), validator);
		}

		private IValidator get(Object fromClass, Object toClass) {
			IValidator result = (IValidator) validators.get(new Pair(fromClass,
					toClass));
			if (result != null)
				return result;
			if (fromClass != null && toClass != null && fromClass == toClass) {
				return new IValidator() {
					@Override
					public IStatus validate(Object value) {
						return Status.OK_STATUS;
					}
				};
			}
			return new IValidator() {
				@Override
				public IStatus validate(Object value) {
					return Status.OK_STATUS;
				}
			};
		}
	}

	private static final String BOOLEAN_TYPE = "java.lang.Boolean.TYPE"; //$NON-NLS-1$

	private static final String SHORT_TYPE = "java.lang.Short.TYPE"; //$NON-NLS-1$

	private static final String BYTE_TYPE = "java.lang.Byte.TYPE"; //$NON-NLS-1$

	private static final String DOUBLE_TYPE = "java.lang.Double.TYPE"; //$NON-NLS-1$

	private static final String FLOAT_TYPE = "java.lang.Float.TYPE"; //$NON-NLS-1$

	private static final String INTEGER_TYPE = "java.lang.Integer.TYPE"; //$NON-NLS-1$

	private static final String LONG_TYPE = "java.lang.Long.TYPE"; //$NON-NLS-1$

	private static final String CHARACTER_TYPE = "java.lang.Character.TYPE"; //$NON-NLS-1$

	private static Map converterMap;

	private static Class autoboxed(Class clazz) {
		if (clazz == Float.TYPE)
			return Float.class;
		else if (clazz == Double.TYPE)
			return Double.class;
		else if (clazz == Short.TYPE)
			return Short.class;
		else if (clazz == Integer.TYPE)
			return Integer.class;
		else if (clazz == Long.TYPE)
			return Long.class;
		else if (clazz == Byte.TYPE)
			return Byte.class;
		else if (clazz == Boolean.TYPE)
			return Boolean.class;
		else if (clazz == Character.TYPE)
			return Character.class;
		return clazz;
	}

	final protected void checkAssignable(Object toType, Object fromType,
			String errorString) {
		Boolean assignableFromModelToModelConverter = isAssignableFromTo(
				fromType, toType);
		if (assignableFromModelToModelConverter != null
				&& !assignableFromModelToModelConverter.booleanValue()) {
			throw new BindingException(errorString
					+ " Expected: " + fromType + ", actual: " + toType); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

	private IConverter createConverter(Object fromType, Object toType) {
		if (!(fromType instanceof Class) || !(toType instanceof Class)) {
			return new DefaultConverter(fromType, toType);
		}
		Class toClass = (Class) toType;
		Class originalToClass = toClass;
		if (toClass.isPrimitive()) {
			toClass = autoboxed(toClass);
		}
		Class fromClass = (Class) fromType;
		Class originalFromClass = fromClass;
		if (fromClass.isPrimitive()) {
			fromClass = autoboxed(fromClass);
		}
		if (!((Class) toType).isPrimitive()
				&& toClass.isAssignableFrom(fromClass)) {
			return new IdentityConverter(originalFromClass, originalToClass);
		}
		if (((Class) fromType).isPrimitive() && ((Class) toType).isPrimitive()
				&& fromType.equals(toType)) {
			return new IdentityConverter(originalFromClass, originalToClass);
		}
		Map converterMap = getConverterMap();
		Class[] supertypeHierarchyFlattened = ClassLookupSupport
				.getTypeHierarchyFlattened(fromClass);
		for (int i = 0; i < supertypeHierarchyFlattened.length; i++) {
			Class currentFromClass = supertypeHierarchyFlattened[i];
			if (currentFromClass == toType) {
				return new IdentityConverter(fromClass, toClass);
			}
			Pair key = new Pair(getKeyForClass(fromType, currentFromClass),
					getKeyForClass(toType, toClass));
			Object converterOrClassname = converterMap.get(key);
			if (converterOrClassname instanceof IConverter) {
				return (IConverter) converterOrClassname;
			} else if (converterOrClassname instanceof String) {
				String classname = (String) converterOrClassname;
				Class converterClass;
				try {
					converterClass = Class.forName(classname);
					IConverter result = (IConverter) converterClass
							.newInstance();
					converterMap.put(key, result);
					return result;
				} catch (Exception e) {
					Policy
							.getLog()
							.log(
									new Status(
											IStatus.ERROR,
											Policy.JFACE_DATABINDING,
											0,
											"Error while instantiating default converter", e)); //$NON-NLS-1$
				}
			}
		}
		if (fromClass.isAssignableFrom(toClass)) {
			return new IdentityConverter(originalFromClass, originalToClass);
		}
		return new DefaultConverter(fromType, toType);
	}

	private static Map getConverterMap() {
		if (converterMap == null) {
			NumberFormat integerFormat = NumberFormat.getIntegerInstance();
			NumberFormat numberFormat = NumberFormat.getNumberInstance();

			converterMap = new HashMap();
			converterMap
					.put(
							new Pair("java.util.Date", "java.lang.String"), "org.eclipse.core.internal.databinding.conversion.DateToStringConverter"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Boolean"), "org.eclipse.core.internal.databinding.conversion.StringToBooleanConverter"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Byte"), StringToByteConverter.toByte(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.String", "java.util.Date"), "org.eclipse.core.internal.databinding.conversion.StringToDateConverter"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Short"), StringToShortConverter.toShort(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Character"), StringToCharacterConverter.toCharacter(false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Integer"), StringToNumberConverter.toInteger(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Double"), StringToNumberConverter.toDouble(numberFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Long"), StringToNumberConverter.toLong(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.String", "java.lang.Float"), StringToNumberConverter.toFloat(numberFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.String", "java.math.BigInteger"), StringToNumberConverter.toBigInteger(integerFormat)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.Integer", "java.lang.String"), NumberToStringConverter.fromInteger(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.Long", "java.lang.String"), NumberToStringConverter.fromLong(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.Double", "java.lang.String"), NumberToStringConverter.fromDouble(numberFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.Float", "java.lang.String"), NumberToStringConverter.fromFloat(numberFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.math.BigInteger", "java.lang.String"), NumberToStringConverter.fromBigInteger(integerFormat)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.Byte", "java.lang.String"), IntegerToStringConverter.fromByte(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.Short", "java.lang.String"), IntegerToStringConverter.fromShort(integerFormat, false)); //$NON-NLS-1$//$NON-NLS-2$
			converterMap
					.put(
							new Pair("java.lang.Character", "java.lang.String"), CharacterToStringConverter.fromCharacter(false)); //$NON-NLS-1$//$NON-NLS-2$

			converterMap
					.put(
							new Pair("java.lang.Object", "java.lang.String"), "org.eclipse.core.internal.databinding.conversion.ObjectToStringConverter"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

			converterMap
					.put(
							new Pair("java.lang.String", INTEGER_TYPE), StringToNumberConverter.toInteger(integerFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(INTEGER_TYPE, "java.lang.Integer"), new IdentityConverter(Integer.TYPE, Integer.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(INTEGER_TYPE, "java.lang.Object"), new IdentityConverter(Integer.TYPE, Object.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(INTEGER_TYPE, "java.lang.String"), NumberToStringConverter.fromInteger(integerFormat, true)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair("java.lang.String", BYTE_TYPE), StringToByteConverter.toByte(integerFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(BYTE_TYPE, "java.lang.Byte"), new IdentityConverter(Byte.TYPE, Byte.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(BYTE_TYPE, "java.lang.String"), IntegerToStringConverter.fromByte(integerFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(BYTE_TYPE, "java.lang.Object"), new IdentityConverter(Byte.TYPE, Object.class)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair("java.lang.String", DOUBLE_TYPE), StringToNumberConverter.toDouble(numberFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(DOUBLE_TYPE, "java.lang.String"), NumberToStringConverter.fromDouble(numberFormat, true)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair(DOUBLE_TYPE, "java.lang.Double"), new IdentityConverter(Double.TYPE, Double.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(DOUBLE_TYPE, "java.lang.Object"), new IdentityConverter(Double.TYPE, Object.class)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair("java.lang.String", BOOLEAN_TYPE), "org.eclipse.core.internal.databinding.conversion.StringToBooleanPrimitiveConverter"); //$NON-NLS-1$ //$NON-NLS-2$
			converterMap
					.put(
							new Pair(BOOLEAN_TYPE, "java.lang.Boolean"), new IdentityConverter(Boolean.TYPE, Boolean.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(BOOLEAN_TYPE, "java.lang.String"), new ObjectToStringConverter(Boolean.TYPE)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(BOOLEAN_TYPE, "java.lang.Object"), new IdentityConverter(Boolean.TYPE, Object.class)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair("java.lang.String", FLOAT_TYPE), StringToNumberConverter.toFloat(numberFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(FLOAT_TYPE, "java.lang.String"), NumberToStringConverter.fromFloat(numberFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(FLOAT_TYPE, "java.lang.Float"), new IdentityConverter(Float.TYPE, Float.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(FLOAT_TYPE, "java.lang.Object"), new IdentityConverter(Float.TYPE, Object.class)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair("java.lang.String", SHORT_TYPE), StringToShortConverter.toShort(integerFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(SHORT_TYPE, "java.lang.Short"), new IdentityConverter(Short.TYPE, Short.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(SHORT_TYPE, "java.lang.String"), IntegerToStringConverter.fromShort(integerFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(SHORT_TYPE, "java.lang.Object"), new IdentityConverter(Short.TYPE, Object.class)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair("java.lang.String", LONG_TYPE), StringToNumberConverter.toLong(integerFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(LONG_TYPE, "java.lang.String"), NumberToStringConverter.fromLong(integerFormat, true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(LONG_TYPE, "java.lang.Long"), new IdentityConverter(Long.TYPE, Long.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(LONG_TYPE, "java.lang.Object"), new IdentityConverter(Long.TYPE, Object.class)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair("java.lang.String", CHARACTER_TYPE), StringToCharacterConverter.toCharacter(true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(CHARACTER_TYPE, "java.lang.Character"), new IdentityConverter(Character.TYPE, Character.class)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(CHARACTER_TYPE, "java.lang.String"), CharacterToStringConverter.fromCharacter(true)); //$NON-NLS-1$
			converterMap
					.put(
							new Pair(CHARACTER_TYPE, "java.lang.Object"), new IdentityConverter(Character.TYPE, Object.class)); //$NON-NLS-1$

			converterMap
					.put(
							new Pair(
									"org.eclipse.core.runtime.IStatus", "java.lang.String"), "org.eclipse.core.internal.databinding.conversion.StatusToStringConverter"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

			addNumberToByteConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToByteConverters(converterMap, numberFormat, floatClasses);

			addNumberToShortConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToShortConverters(converterMap, numberFormat, floatClasses);

			addNumberToIntegerConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToIntegerConverters(converterMap, numberFormat,
					floatClasses);

			addNumberToLongConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToLongConverters(converterMap, numberFormat, floatClasses);

			addNumberToFloatConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToFloatConverters(converterMap, numberFormat, floatClasses);

			addNumberToDoubleConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToDoubleConverters(converterMap, numberFormat,
					floatClasses);

			addNumberToBigIntegerConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToBigIntegerConverters(converterMap, numberFormat,
					floatClasses);

			addNumberToBigDecimalConverters(converterMap, integerFormat,
					integerClasses);
			addNumberToBigDecimalConverters(converterMap, numberFormat,
					floatClasses);
		}

		return converterMap;
	}

	private static final Class[] integerClasses = new Class[] { Byte.TYPE,
			Byte.class, Short.TYPE, Short.class, Integer.TYPE, Integer.class,
			Long.TYPE, Long.class, BigInteger.class };

	private static final Class[] floatClasses = new Class[] { Float.TYPE,
			Float.class, Double.TYPE, Double.class, BigDecimal.class };

	private static void addNumberToByteConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {

		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(Byte.class) && !fromType.equals(Byte.TYPE)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map
						.put(new Pair(fromName, BYTE_TYPE),
								new NumberToByteConverter(numberFormat,
										fromType, true));
				map
						.put(new Pair(fromName, Byte.class.getName()),
								new NumberToByteConverter(numberFormat,
										fromType, false));
			}
		}
	}

	private static void addNumberToShortConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {
		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(Short.class) && !fromType.equals(Short.TYPE)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map
						.put(new Pair(fromName, SHORT_TYPE),
								new NumberToShortConverter(numberFormat,
										fromType, true));
				map.put(new Pair(fromName, Short.class.getName()),
						new NumberToShortConverter(numberFormat, fromType,
								false));
			}
		}
	}

	private static void addNumberToIntegerConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {
		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(Integer.class)
					&& !fromType.equals(Integer.TYPE)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map.put(new Pair(fromName, INTEGER_TYPE),
						new NumberToIntegerConverter(numberFormat, fromType,
								true));
				map.put(new Pair(fromName, Integer.class.getName()),
						new NumberToIntegerConverter(numberFormat, fromType,
								false));
			}
		}
	}

	private static void addNumberToLongConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {
		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(Long.class) && !fromType.equals(Long.TYPE)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map
						.put(new Pair(fromName, LONG_TYPE),
								new NumberToLongConverter(numberFormat,
										fromType, true));
				map
						.put(new Pair(fromName, Long.class.getName()),
								new NumberToLongConverter(numberFormat,
										fromType, false));
			}
		}
	}

	private static void addNumberToFloatConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {
		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(Float.class) && !fromType.equals(Float.TYPE)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map
						.put(new Pair(fromName, FLOAT_TYPE),
								new NumberToFloatConverter(numberFormat,
										fromType, true));
				map.put(new Pair(fromName, Float.class.getName()),
						new NumberToFloatConverter(numberFormat, fromType,
								false));
			}
		}
	}

	private static void addNumberToDoubleConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {
		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(Double.class) && !fromType.equals(Double.TYPE)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map.put(new Pair(fromName, DOUBLE_TYPE),
						new NumberToDoubleConverter(numberFormat, fromType,
								true));
				map.put(new Pair(fromName, Double.class.getName()),
						new NumberToDoubleConverter(numberFormat, fromType,
								false));
			}
		}
	}

	private static void addNumberToBigIntegerConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {
		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(BigInteger.class)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map
						.put(new Pair(fromName, BigInteger.class.getName()),
								new NumberToBigIntegerConverter(numberFormat,
										fromType));
			}
		}
	}

	private static void addNumberToBigDecimalConverters(Map map,
			NumberFormat numberFormat, Class[] fromTypes) {
		for (int i = 0; i < fromTypes.length; i++) {
			Class fromType = fromTypes[i];
			if (!fromType.equals(BigDecimal.class)) {
				String fromName = (fromType.isPrimitive()) ? getKeyForClass(
						fromType, null) : fromType.getName();

				map
						.put(new Pair(fromName, BigDecimal.class.getName()),
								new NumberToBigDecimalConverter(numberFormat,
										fromType));
			}
		}
	}

	private static String getKeyForClass(Object originalValue,
			Class filteredValue) {
		if (originalValue instanceof Class) {
			Class originalClass = (Class) originalValue;
			if (originalClass.equals(Integer.TYPE)) {
				return INTEGER_TYPE;
			} else if (originalClass.equals(Byte.TYPE)) {
				return BYTE_TYPE;
			} else if (originalClass.equals(Boolean.TYPE)) {
				return BOOLEAN_TYPE;
			} else if (originalClass.equals(Double.TYPE)) {
				return DOUBLE_TYPE;
			} else if (originalClass.equals(Float.TYPE)) {
				return FLOAT_TYPE;
			} else if (originalClass.equals(Long.TYPE)) {
				return LONG_TYPE;
			} else if (originalClass.equals(Short.TYPE)) {
				return SHORT_TYPE;
			}
		}
		return filteredValue.getName();
	}

	protected Boolean isAssignableFromTo(Object fromType, Object toType) {
		if (fromType instanceof Class && toType instanceof Class) {
			Class toClass = (Class) toType;
			if (toClass.isPrimitive()) {
				toClass = autoboxed(toClass);
			}
			Class fromClass = (Class) fromType;
			if (fromClass.isPrimitive()) {
				fromClass = autoboxed(fromClass);
			}
			return toClass.isAssignableFrom(fromClass) ? Boolean.TRUE
					: Boolean.FALSE;
		}
		return null;
	}

	protected static final class DefaultConverter implements IConverter {

		private final Object toType;

		private final Object fromType;

		DefaultConverter(Object fromType, Object toType) {
			this.toType = toType;
			this.fromType = fromType;
		}

		@Override
		public Object convert(Object fromObject) {
			return fromObject;
		}

		@Override
		public Object getFromType() {
			return fromType;
		}

		@Override
		public Object getToType() {
			return toType;
		}
	}
}


