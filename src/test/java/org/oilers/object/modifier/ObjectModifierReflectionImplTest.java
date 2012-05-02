package org.oilers.object.modifier;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.oilers.object.modifier.ObjectModifier;
import org.oilers.object.modifier.ObjectModifierFactory;

public class ObjectModifierReflectionImplTest {
	@Test
	public void testChangeFinals() throws Exception {
		ImmutableClass imClass = new ImmutableClass();
		ObjectModifier<ImmutableClass> modifier = ObjectModifierFactory.getInstance();
		String preState = modifier.getState(imClass);
		modifier.changeFields(imClass);
		String postState = modifier.getState(imClass);
		assertFalse(preState.equals(postState));
	}
}
