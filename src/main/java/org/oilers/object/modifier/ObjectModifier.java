package org.oilers.object.modifier;

import java.lang.reflect.Field;

public interface ObjectModifier<T> {
	String getState(T obj) throws ReflectiveOperationException;

	void changeFields(T obj) throws ReflectiveOperationException;

	String getFieldState(T obj, Field field) throws ReflectiveOperationException;

	void changeField(T obj, String fieldName, Object newValue) throws ReflectiveOperationException;

	void changeField(T obj, Field field, Object newValue) throws ReflectiveOperationException;
}
