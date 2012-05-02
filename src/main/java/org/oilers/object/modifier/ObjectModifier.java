package org.oilers.object.modifier;

import java.lang.reflect.Field;

public interface ObjectModifier<T> {
	String getState(T obj) throws Exception;

	void changeFields(T obj) throws Exception;

	String getFieldState(T obj, Field field) throws Exception;

	void changeField(T obj, String fieldName, Object newValue) throws Exception;

	void changeField(T obj, Field field, Object newValue) throws Exception;
}
