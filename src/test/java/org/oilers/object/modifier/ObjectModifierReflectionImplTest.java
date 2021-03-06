package org.oilers.object.modifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ObjectModifierReflectionImplTest {
	@Test
	public void testChangeFinals() {
		ImmutableClass imClass = new ImmutableClass();
		ObjectModifier<ImmutableClass> modifier = ObjectModifierFactory.getInstance();
		try {
			String preState = modifier.getState(imClass);
			modifier.changeFields(imClass);
			String postState = modifier.getState(imClass);
			assertFalse(preState.equals(postState));
			assertFalse(imClass.equals(new ImmutableClass()));
		} catch (ReflectiveOperationException e) {
			fail();
		}
	}
}
