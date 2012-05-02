package org.oilers.object.modifier;

import java.util.HashMap;
import java.util.Map;

public final class ObjectModifierFactory {
	public static final <T> ObjectModifier<T> getInstance() {
		Map<Class<?>, Object> tempReplacementObjects = new HashMap<Class<?>, Object>();
		tempReplacementObjects.put(Object.class, new NullObject());
		tempReplacementObjects.put(String.class, "changed!!");
		tempReplacementObjects.put(Boolean.class, Boolean.FALSE);
		tempReplacementObjects.put(Long.class, Long.MAX_VALUE);
		tempReplacementObjects.put(Integer.class, Integer.MAX_VALUE);
		return new ObjectModifierReflectionImpl<T>(tempReplacementObjects);
	}

	public static final <T> ObjectModifier<T> getInstance(Map<Class<?>, Object> replacementObjects) {
		return new ObjectModifierReflectionImpl<T>(replacementObjects);
	}

	private ObjectModifierFactory() {
		// No instantiation of this class is allowed
	}

	private static class NullObject {}
}
