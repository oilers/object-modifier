package org.oilers.object.modifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;

final class ObjectModifierReflectionImpl<T> implements ObjectModifier<T> {
	private final Map<Class<?>, Object> replacementObjects;

	protected ObjectModifierReflectionImpl(Map<Class<?>, Object> replacementObjects) {
		this.replacementObjects = Collections.unmodifiableMap(replacementObjects);
	}

	@Override
	public void changeFields(T obj) throws ReflectiveOperationException {
		for (Field field : obj.getClass().getDeclaredFields())
			if (isFieldEligbleForChange(field))
				changeField(obj, field, replacementObjects.get(field.getType()));
	}

	private boolean isFieldEligbleForChange(Field field) {
		return !field.isSynthetic() && replacementObjects.containsKey(field.getType());
	}

	@Override
	public void changeField(T obj, Field field, Object value) throws ReflectiveOperationException {
		boolean isAccessible = field.isAccessible();
		field.setAccessible(true);
		field.set(obj, value);
		field.setAccessible(isAccessible);
	}

	@Override
	public String getState(T obj) throws ReflectiveOperationException {
		StringBuffer objectState = new StringBuffer("State of ").append(obj).append('\n');
		for (Field field : obj.getClass().getDeclaredFields())
			if (!field.isSynthetic())
				objectState.append(getFieldState(obj, field)).append('\n');
		objectState.deleteCharAt(objectState.length() - 1);
		return objectState.toString();
	}

	@Override
	public String getFieldState(T obj, Field field) throws ReflectiveOperationException {
		String propertyName = getPropertyName(field.getName());
		Method method = findAccesorMethod(obj, propertyName);
		return getFieldString(field, obj, method).toString();
	}

	private String getPropertyName(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	private Method findAccesorMethod(T obj, String propertyName) {
		Method method = null;
		for (Method testMethod : obj.getClass().getMethods())
			if (isAccessorMethodForProperty(propertyName, testMethod))
				method = testMethod;
		return method;
	}

	private boolean isAccessorMethodForProperty(String propertyName, Method testMethod) {
		return testMethod.getName().equals("get" + propertyName)
				|| testMethod.getName().equals("is" + propertyName);
	}

	private StringBuffer getFieldString(Field field, T obj, Method method)
			throws ReflectiveOperationException {
		return new StringBuffer(Modifier.toString(field.getModifiers())).append(" ")
				.append(field.getName()).append(" = ").append(invokeMethod(obj, method));
	}

	private Object invokeMethod(T obj, Method method) throws ReflectiveOperationException {
		if (!isMethodNullOrNonPublic(method)) {
			boolean isAccessable = method.isAccessible();
			method.setAccessible(true);
			Object result = method.invoke(obj);
			method.setAccessible(isAccessable);
			return result;
		} else
			return "No public accessor";
	}

	private boolean isMethodNullOrNonPublic(Method method) {
		return method == null || !Modifier.isPublic(method.getModifiers());
	}

	@Override
	public void changeField(T obj, String fieldName, Object newValue)
			throws ReflectiveOperationException {
		Field field = findField(fieldName, obj.getClass());
		changeField(obj, field, newValue);
	}

	private Field findField(String fieldName, Class<?> clazz) throws ReflectiveOperationException {
		return clazz.getDeclaredField(fieldName);
	}
}
